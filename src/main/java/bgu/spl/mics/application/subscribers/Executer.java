package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.ExecuterExecutingBroadcast;
import bgu.spl.mics.application.messages.MTerminateEvent;

public class Executer extends Subscriber {

    private int realnumOfM;
    private int currNumOfM;

    public Executer(int numOfM){
        super("Executer");
        this.realnumOfM = numOfM;
        currNumOfM = 0;
    }

    protected void initialize() {
        subscribeEvent(MTerminateEvent.class, event ->{
            currNumOfM++;
            if(realnumOfM == currNumOfM){
                getSimplePublisher().sendBroadcast(new ExecuterExecutingBroadcast());
                terminate();
            }
            complete(event,"execomplete");
        });
    }
}
