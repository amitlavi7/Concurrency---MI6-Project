package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.List;

public class MissionReceivedEvent implements Event<String> {

    private MissionInfo missionInfo;
    private Report report;


    public MissionReceivedEvent(MissionInfo missionInfo, int currentTick) {
        this.missionInfo = missionInfo;
        report = new Report();
        report.setTimeIssued(currentTick);
    }

    public Report getReport(){
        return report;
    }

    public  MissionInfo getMissionInfo () {
        return missionInfo;
    }

}


//    private String missionName;
//    private List<String> agentsNumbers;
//    private String gadget;
//
//    public MissionReceivedEvent(String missionName, List<String> agentsNumbers, String gadget) {
//        this.missionName = missionName;
//        this.agentsNumbers = agentsNumbers;
//        this.gadget = gadget;
//    }
//
//    public String getMissionName() {
//        return missionName;
//    }
//
//    public List<String> getAgentsNumbers() {
//        return agentsNumbers;
//    }
//
//    public String getGadget() {
//        return gadget;
//    }
//
//}
