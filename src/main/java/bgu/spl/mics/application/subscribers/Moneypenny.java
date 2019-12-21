package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;

/**
 * Only this type of Subscriber can access the squad.
 * There are several Moneypenny-instances - each of them holds a unique serial number that will later be printed on the report.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Moneypenny extends Subscriber {
	private int id;
	private Squad squad = Squad.getInstance();

	public Moneypenny(int id) {
		super("Moneypenny");
		this.id = id;
	}

	@Override
	protected void initialize() {
		System.out.println("Monneypenny " + id + ": initialized");
		if(id % 2 == 0) {
			subscribeEvent(AgentsAvailableEvent.class, event -> {
				System.out.println("Monneypenny " + id + ": AgentsAvailableEvent");
				if (!squad.getAgents(event.getAgentsNumbers())) {
					try {
						wait();
					} catch (Exception ignored) {
					}
//				complete(event, "agentsAvailableFailed");
				} else {
					complete(event, "agentsAvailableSucceed");
				}
			});
		}
		else {
			subscribeEvent(SendAgentsEvent.class, event -> {
				squad.sendAgents(event.getAgentsToSend(), event.getDurationForMission());
				System.out.println("Monneypenny " + id + ": SendAgentsEvent");
				complete(event, "missionCompleted!");
				squad.releaseAgents(event.getAgentsToSend());
			});
			subscribeEvent(ReleaseAgentsEvent.class, event -> {
				squad.releaseAgents(event.getAgentsNumbers());
				System.out.println("Monneypenny " + id + ": ReleaseAgentsEvent");
				complete(event, "mission Time is up!");
			});
		}
		subscribeBroadcast(TimeIsUp.class, event ->{
			terminate();
		});
	}
}
