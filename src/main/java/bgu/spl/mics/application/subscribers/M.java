package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Subscriber;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {

	public M() {
		super("Change_This_Name");
//		subscribeEvent(MissionReceivedEvent, Callback.call(m));
	}

	@Override
	protected void initialize() {

	}

}
