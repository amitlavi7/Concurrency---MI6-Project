package bgu.spl.mics;

import java.util.concurrent.*;


/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {

	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Message>> subscribersMissionQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Subscriber, LinkedBlockingQueue <Class<? extends Message>>> subscribersTopicQueues = new ConcurrentHashMap <>();
	private ConcurrentHashMap<Class<? extends Event<?>>, LinkedBlockingQueue<Subscriber>> eventHandlerQueues = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Class<? extends Broadcast>, LinkedBlockingQueue<Subscriber>> broadcastQueue = new ConcurrentHashMap<>();
	private ConcurrentHashMap<Event<?>,Future> holdsFuture = new ConcurrentHashMap<>();
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
		if(holdsFuture.containsKey(e)){
			holdsFuture.get(e).resolve(result);
			holdsFuture.remove(e);
		}

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if(broadcastQueue.containsKey(b.getClass())) {
			LinkedBlockingQueue<Subscriber> temp = new LinkedBlockingQueue<>(broadcastQueue.get(b.getClass()));
			while (!temp.isEmpty()) {
				Subscriber sub = temp.poll();
				subscribersMissionQueues.get(sub).add(b);
			}
		}

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		if(eventHandlerQueues.containsKey(e.getClass()) && !eventHandlerQueues.get(e.getClass()).isEmpty()){
			synchronized (eventHandlerQueues.get(e.getClass())) {
				Subscriber subGetMission = eventHandlerQueues.get(e.getClass()).poll();
				Future<T> future = new Future<>();
				holdsFuture.putIfAbsent(e, future);
				if (subGetMission != null) {
					eventHandlerQueues.get(e.getClass()).add(subGetMission);
					subscribersMissionQueues.get(subGetMission).add(e);
					return future;
				}
				else {
					future.resolve(null);
				}
			}
		}
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
			Class<?> mes = temp.poll();
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
		return subscribersMissionQueues.get(m).poll();


	}

	

}
