package bgu.spl.mics;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.publishers.ExampleMessageSender;
import bgu.spl.mics.example.subscribers.ExampleBroadcastSubscriber;
import bgu.spl.mics.example.subscribers.ExampleEventHandlerSubscriber;
import bgu.spl.mics.application.subscribers.Q;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Intelligence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageBrokerTest {
    private MessageBroker broker;
    private ExampleBroadcast exampleBroadcast;
    private ExampleEvent exampleEvent;
    private ExampleMessageSender exampleMessageSender;
    private ExampleBroadcastSubscriber exampleBroadcastSubscriber;
    private ExampleEventHandlerSubscriber exampleEventHandlerSubscriber;
    @BeforeEach
    public void setUp(){
        String[] array = new String[2];
        broker = new MessageBrokerImpl();
        exampleEvent = new ExampleEvent("Amit");
        exampleEventHandlerSubscriber = new ExampleEventHandlerSubscriber("Gili", array);
        exampleBroadcast = new ExampleBroadcast("refael");
        exampleBroadcastSubscriber = new ExampleBroadcastSubscriber("falafel", array);

    }

    @Test
    public void test(){
//        Subscriber q = new Q();
//        Subscriber m = new M();
//        Subscriber moneypenny = new Moneypenny();
//        Subscriber intel = new Intelligence();
//        assertNotNull(broker);
//        broker.register(q);
//        broker.register(m);
//        broker.register(moneypenny);
//        broker.subscribeEvent(exampleEvent.getClass(), exampleEventHandlerSubscriber);
//        assertNotNull(broker.sendEvent(exampleEvent));
//        broker.subscribeBroadcast(exampleBroadcast.getClass(),exampleBroadcastSubscriber);

    }
}
