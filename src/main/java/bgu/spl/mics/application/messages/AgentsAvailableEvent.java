package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;

public class AgentsAvailableEvent implements Event<String> {
    private List<String> agentNumbers;
    private boolean isAvailable;
    private Report report;

    public AgentsAvailableEvent (List<String> agentNumber, Report report) {
        this.report = report;
        this.agentNumbers = agentNumber;
    }

    public Report getReport(){
        return report;
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
