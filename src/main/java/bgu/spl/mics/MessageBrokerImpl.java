package bgu.spl.mics;

//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.*;

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Message>> subscribersMissionQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Message>> subscribersTopicQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Class<? extends Event>, LinkedBlockingQueue<Subscriber>> eventHandlerQueues = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Broadcast>, LinkedBlockingQueue<Subscriber>> broadcastQueue = new ConcurrentHashMap<>();

	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		//TODO: Implement this
		return null;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(Subscriber m) {
		subscribersMissionQueues.putIfAbsent(m, new LinkedBlockingQueue<>());
		subscribersTopicQueues.putIfAbsent(m, new LinkedBlockingQueue<>());
	}

	@Override
	public void unregister(Subscriber m) {
		LinkedBlockingQueue <Message> temp = subscribersTopicQueues.get(m);
		subscribersMissionQueues.remove(m);
		subscribersTopicQueues.remove(m);
		while(!temp.isEmpty()){
			Object mes = temp.poll();
			if(mes instanceof Broadcast){
				broadcastQueue.get(mes).remove(m);
			}
			else{
				eventHandlerQueues.get(mes).remove(m);
			}

		}

	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
