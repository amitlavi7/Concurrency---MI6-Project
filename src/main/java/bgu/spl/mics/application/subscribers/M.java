package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	private int id;
	private int time;

	public M(int id) {
		super("M");
		this.id = id;
	}



	@Override
	protected void initialize() {
		System.out.println("M initialized");
		subscribeEvent(MissionReceivedEvent.class, event -> {
			System.out.println("M's MissionReceivedEvent callback");//harta
			Future agentsResolved = getSimplePublisher().sendEvent(new AgentsAvailableEvent(event.getMissionInfo().getSerialAgentsNumbers()));
			if(agentsResolved.get() == "agentsSucceed") {
				Future gadgetResolved = getSimplePublisher().sendEvent(new GadgetAvailableEvent(event.getMissionInfo().getGadget()));
				if (gadgetResolved.get() == "gadgetSucceed") {
					if(event.getMissionInfo().getTimeExpired()>time) {
						getSimplePublisher().sendEvent(new SendAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers(),event.getMissionInfo().getDuration()));
						}
					}
				getSimplePublisher().sendEvent(new ReleaseAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers()));
			}
		});

		subscribeBroadcast(TickBroadcast.class, event ->{
			time = event.getCurrentTick();
		});

		subscribeBroadcast(TimeIsUp.class, event ->{
			terminate();
		});
	}
}
