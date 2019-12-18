package bgu.spl.mics;

import java.util.concurrent.*;


/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Class<? extends Message>>> subscribersMissionQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Class<? extends Message>>> subscribersTopicQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Class<? extends Event>, LinkedBlockingQueue<Subscriber>> eventHandlerQueues = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Broadcast>, LinkedBlockingQueue<Subscriber>> broadcastQueue = new ConcurrentHashMap<>();
	private static class MessageBrokerHolder {
		private static MessageBroker instance = new MessageBrokerImpl();
	}
	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return MessageBrokerHolder.instance;
	}

	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		eventHandlerQueues.putIfAbsent(type, new LinkedBlockingQueue<>());
		eventHandlerQueues.get(type).add(m);
		subscribersTopicQueues.get(m).add(type);
		subscribersMissionQueues.get(m).add(type);
//		if(!eventHandlerQueues.contains(type)){
//			LinkedBlockingQueue<Subscriber> toPush = new LinkedBlockingQueue<Subscriber>();
//			eventHandlerQueues.putIfAbsent(type, toPush);
//		}
//		else{
//			synchronized (eventHandlerQueues.get(type)){
//				eventHandlerQueues.get(type).add(m);
//			}
//		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
			broadcastQueue.putIfAbsent(type, new LinkedBlockingQueue<>());
			broadcastQueue.get(type).add(m);
			subscribersTopicQueues.get(m).add(type);

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		LinkedBlockingQueue <Subscriber> temp = broadcastQueue.get(b);
		while(!temp.isEmpty()){
			Subscriber sub = temp.poll();
			subscribersMissionQueues.get(sub).add(b.getClass());//need yo be checked

		}

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if(eventHandlerQueues.contains(e) && !eventHandlerQueues.get(e).isEmpty()){
			synchronized (eventHandlerQueues.get(e)) {
				Subscriber subGetMission = eventHandlerQueues.get(e).poll();
				eventHandlerQueues.get(e).add(subGetMission);
				subscribersMissionQueues.get(subGetMission).add(e.getClass());
			}

		}
;''
			return null;

	}

	@Override
	public void register(Subscriber m) {
		subscribersMissionQueues.putIfAbsent(m, new LinkedBlockingQueue<>());
		subscribersTopicQueues.putIfAbsent(m, new LinkedBlockingQueue<>());
	}

	@Override
	public void unregister(Subscriber m) {
		LinkedBlockingQueue <Class<? extends Message>> temp = subscribersTopicQueues.get(m);
		subscribersMissionQueues.remove(m);
		subscribersTopicQueues.remove(m);
		while(!temp.isEmpty()){
			Class mes = temp.poll();
			if(Event.class.isAssignableFrom(mes)){
				eventHandlerQueues.get(mes).remove(m);
			}
			else{
				broadcastQueue.get(mes).remove(m);
			}

		}

	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
//		if(!subscribersMissionQueues.contains(m)){
//			throw new IllegalStateException("The subscriber was never register");
//		}
//		else {
//			try {
//				return subscribersMissionQueues.get(m).poll();
//			} catch (InterruptedException exception) {
//				return null;
//			}
//		}
		return null;
	}

	

}
