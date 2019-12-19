package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.SendAgentsEvent;
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

	private Squad squad = Squad.getInstance();

	public Moneypenny() {
		super("Moneypenny");

	}

	@Override
	protected void initialize() {
		subscribeEvent(AgentsAvailableEvent.class, event -> {
			if (!squad.getAgents(event.getAgentsNumbers())) {
				try {
					wait();
				} catch (Exception ignored) {
				}
//				complete(event, "agentsAvailableFailed");
			}
			else {
				complete(event, "agentsAvailableSucceed");
			}
		});
		subscribeEvent(SendAgentsEvent.class, event ->{
			squad.sendAgents(event.getAgentsToSend(),event.getDurationForMission());
			complete(event, "missionCompleted!");
			squad.releaseAgents(event.getAgentsToSend());
		});
	}
}
