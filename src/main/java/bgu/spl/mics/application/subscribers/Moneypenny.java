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
	private final Squad squad = Squad.getInstance();

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
					event.getReport().setMoneypenny(id);
					event.getReport().setAgentsNames(squad.getAgentsNames(event.getReport().getAgentsSerialNumbers()));
				if (squad.getAgents(event.getAgentsNumbers())) {
//					synchronized (squad) {
//						while (!squad.getAgents(event.getAgentsNumbers())) {
//							try {
//								squad.wait();
//							} catch (InterruptedException ignored) {
//							}
//						}
//					}
					complete(event, "agentsAvailableSucceed");
				}
				else {
					complete(event, "agentIsNotExist");

				}
			});
		}
		else {
			subscribeEvent(SendAgentsEvent.class, event -> {
				System.out.println("Monneypenny " + id + ": SendAgentsEvent");
				squad.sendAgents(event.getAgentsToSend(), event.getDurationForMission());
//				squad.releaseAgents(event.getAgentsToSend());
				complete(event, "agentsSent");
			});
			subscribeEvent(ReleaseAgentsEvent.class, event -> {
				System.out.println("Monneypenny " + id + ": ReleaseAgentsEvent");

				squad.releaseAgents(event.getAgentsNumbers());
				complete(event, "agentsReleased");
			});
		}
		subscribeBroadcast(ExecuterExecutingBroadcast.class, event ->{
			System.out.println("Monneypenny " + id + ": is terminating");
			terminate();

//			subscribeBroadcast(TimeIsUp.class, event ->{
//				System.out.println("Monneypenny " + id + ": is terminating");
//				squad.releaseAgents(null);
//				terminate();
		});
	}
}
