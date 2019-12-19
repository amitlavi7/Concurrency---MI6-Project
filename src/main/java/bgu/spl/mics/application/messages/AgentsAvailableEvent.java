package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class AgentsAvailableEvent implements Event<String> {
    private List<String> agentNumbers;
    private boolean isAvailable;

    public AgentsAvailableEvent (List<String> agentNumber) {
        this.agentNumbers = agentNumber;
    }

    public List<String> getAgentsNumbers() {
        return agentNumbers;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
