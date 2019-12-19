package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.MissionReceivedEvent;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	public M() {
		super("M");
	}

	@Override
	protected void initialize() {
		subscribeEvent(MissionReceivedEvent.class, (MissionReceivedEvent event) -> {
			System.out.println("M's MissionReceivedEvent callback");
			Future agentsResolved = getSimplePublisher().sendEvent(new AgentsAvailableEvent(event.getAgentsNumbers()));
			if(agentsResolved.get() == "agentsSucceed") {
				Future gadgetResolved = getSimplePublisher().sendEvent(new GadgetAvailableEvent(event.getGadget()));
				if (gadgetResolved.get() == "gadgetSucceed") {

				}
			}
		});
	}
}
