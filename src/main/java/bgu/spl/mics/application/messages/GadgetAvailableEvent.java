package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class GadgetAvailableEvent implements Event {

    private String gadget;
    private boolean isAvailable;

    public GadgetAvailableEvent (String gadget) {
        this.gadget = gadget;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getGadget() {return gadget;}
}
