package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

//TODO all of this shit

public class AgentsAvailableEvent implements Event {
    private String agentNumber;
    private boolean isAvailable;

    public AgentsAvailableEvent (String agentNumber) {
        this.agentNumber = agentNumber;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
