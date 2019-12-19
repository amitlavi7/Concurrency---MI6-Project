package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Agent;

import java.util.List;

public class SendAgentsEvent implements Event <String> {
    private List<String> agentsToSend;
    private int durationForMission;


    public SendAgentsEvent(List<String> agentsToSend, int durationForMission){
        this.agentsToSend = agentsToSend;
        this.durationForMission = durationForMission;
    }

    public List<String> getAgentsToSend(){return agentsToSend;}

    public int getDurationForMission(){return durationForMission;}
}
