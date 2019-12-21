package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Report;

public class GadgetAvailableEvent implements Event<String> {

    private String gadget;
    private boolean isAvailable;
    private Report report;

    public GadgetAvailableEvent (String gadget, Report report) {
        this.report = report;
        this.gadget = gadget;
    }

    public Report getReport(){
        return report;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getGadget() {return gadget;}
}
