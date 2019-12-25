package bgu.spl.mics.application.publishers;

import bgu.spl.mics.MessageBrokerImpl;
import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsUp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link .Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class TimeService extends Publisher {

	private int timeTicks;
	private Timer timer;
	private int currenttime;

	public TimeService(int timeTicks) {
		super("TimeService");
		this.timeTicks = timeTicks;
		timer = new Timer();
		currenttime = 0;
	}

	@Override
	protected void initialize() {
		System.out.println("time service initialized");
	}


	@Override
	public void run() {
		initialize();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (timeTicks >= currenttime) {
					MessageBrokerImpl.getInstance().sendBroadcast(new TickBroadcast(currenttime));
					currenttime++;
				}
				else{
					timer.cancel();
					System.out.println("time is up");
					MessageBrokerImpl.getInstance().sendBroadcast(new TimeIsUp());
				}
			}
		}, 0, 100);
	}
}