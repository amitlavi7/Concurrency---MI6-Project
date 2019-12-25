package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Diary;

import java.util.concurrent.TimeUnit;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	private int id;
	private int time;
	private int totalTime;
	private Diary diary = Diary.getInstance();

	public M(int id, int totalTime) {
		super("M");
		this.id = id;
		this.totalTime = totalTime;
	}



	@Override
	protected void initialize() {
		System.out.println("M " + id + ": initialized");
		subscribeEvent(MissionReceivedEvent.class, event -> {
			System.out.println("M " + id + ": MissionReceivedEvent event");//harta
			event.getReport().setTimeCreated(time);
			event.getReport().setAgentsSerialNumbers(event.getMissionInfo().getSerialAgentsNumbers());
			event.getReport().setM(id);
//			event.getReport().setAgentsNames(event.getMissionInfo().get); add the names
			event.getReport().setGadgetName(event.getMissionInfo().getGadget());
			event.getReport().setMissionName(event.getMissionInfo().getMissionName());
			System.out.println("dd");
			diary.incrementTotal();
			Future agentsResolved = getSimplePublisher().sendEvent(new AgentsAvailableEvent(event.getMissionInfo().getSerialAgentsNumbers(), event.getReport()));
			if(agentsResolved != null && agentsResolved.get() != null && agentsResolved.get().equals("agentsAvailableSucceed")) {
				System.out.println(getName() + " " + id + " is in the if number 1");
				System.out.println(getName() + " " + id + " : has Agents that Available");
				Future gadgetResolved = getSimplePublisher().sendEvent(new GadgetAvailableEvent(event.getMissionInfo().getGadget(), event.getReport()));
				if (gadgetResolved != null && gadgetResolved.get() != null && gadgetResolved.get().equals("gadgetSucceed")) {
					if(event.getMissionInfo().getTimeExpired() > time) {
						System.out.println("M " + id + " want to send agents");
						Future agentsSendCheck = getSimplePublisher().sendEvent(new SendAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers(),event.getMissionInfo().getDuration()));
							if(agentsSendCheck != null && agentsSendCheck.get() != null && agentsSendCheck.get().equals("agentsSent")){
								System.out.println("M " + id + " ask from monneypenny to release Agents");
								diary.addReport(event.getReport());
								Future agentsReleaseResolved = getSimplePublisher().sendEvent(new ReleaseAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers()));
								if (agentsReleaseResolved == null) {
									System.out.println("M " + id + " mission failed couldn't release agents");
									complete(event, "missionFailed");
								}
							}
						System.out.println("M " + id + " mission success");
						complete(event,"missionSucceed");
						}
					else {
						getSimplePublisher().sendEvent(new ReleaseAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers()));
						System.out.println("M " + id + " mission failed time expire");
						complete(event, "missionFailed");
					}
					}
				else {
					getSimplePublisher().sendEvent(new ReleaseAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers()));
					System.out.println("M " + id + " mission failed no gadget");
					complete(event, "missionFailed");
				}


			}
			else {
				if (agentsResolved.get(100*totalTime, TimeUnit.MILLISECONDS) == null)
					System.out.println("because of null -----------------------------------------------");
				System.out.println(getName() + id + " failed no agents ");
				complete(event, "missionFailed");
			}
			System.out.println(getName() + id + " event finished");
//			diary.incrementTotal();
			System.out.println("total: " + diary.getTotal());
		});

		subscribeBroadcast(TickBroadcast.class, event ->{
			time = event.getCurrentTick();
		});

		subscribeBroadcast(TimeIsUp.class, event ->{
			System.out.println("M " + id + ": is terminating");
//			diary.printToFile("Diary.json");
//			getSimplePublisher().sendEvent(new MTerminateEvent());
			terminate();
		});
	}
}
