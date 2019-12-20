package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TimeIsUp;
import bgu.spl.mics.application.passiveObjects.Inventory;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {

	private Inventory inventory = Inventory.getInstance();

	public Q() {
		super("Q");
	}

	@Override
	protected void initialize() {
		System.out.println("Q initialized");
		subscribeEvent(GadgetAvailableEvent.class, event -> {
			System.out.println("Q GadgetAvailableEvent");
			System.out.println("");
			if (inventory.getItem(event.getGadget()))
				complete(event, "gadgetSucceed");
			else
				complete(event, "gadgetFailed");
		});
		subscribeBroadcast(TimeIsUp.class, event ->{
			terminate();
		});
	}
}
