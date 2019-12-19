package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.SendAgentsEvent;

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
			System.out.println("M's MissionReceivedEvent callback");//harta
			Future agentsResolved = getSimplePublisher().sendEvent(new AgentsAvailableEvent(event.getMissionInfo().getSerialAgentsNumbers()));
			if(agentsResolved.get() == "agentsSucceed") {
				Future gadgetResolved = getSimplePublisher().sendEvent(new GadgetAvailableEvent(event.getMissionInfo().getGadget()));
				if (gadgetResolved.get() == "gadgetSucceed") {
					//need to check time
					//if statment for time
						getSimplePublisher().sendEvent(new SendAgentsEvent(event.getMissionInfo().getSerialAgentsNumbers(),event.getMissionInfo().getDuration()));
				}
			}
		});
	}
}
