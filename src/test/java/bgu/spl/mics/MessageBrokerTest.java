package bgu.spl.mics;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.publishers.ExampleMessageSender;
import bgu.spl.mics.example.subscribers.ExampleBroadcastSubscriber;
import bgu.spl.mics.example.subscribers.ExampleEventHandlerSubscriber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class MessageBrokerTest {
    private MessageBroker broker;
    private ExampleBroadcast exampleBroadcast;
    private ExampleEvent exampleEvent;
    private ExampleMessageSender exampleMessageSender;
    private ExampleBroadcastSubscriber exampleBroadcastSubscriber;
    private ExampleEventHandlerSubscriber exampleEventHandlerSubscriber;
    @BeforeEach
    public void setUp(){
        broker = MessageBrokerImpl.getInstance();
    }

    @Test
    public void test(){
        //TODO: change this test and add more tests :)
        fail("Not a good test");
    }
}
