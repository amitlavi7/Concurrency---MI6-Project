package bgu.spl.mics.application.subscribers;

//import bgu.spl.mics.Publisher;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TimeIsUp;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A Publisher|Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {
	private LinkedList<MissionInfo> missions;
	private HashMap<Integer, LinkedList<MissionInfo>> missionsHashMap = new HashMap<>();
	int number;


	public Intelligence(int number, JsonArray missionArray) {
		super("Intelligence");
		this.number = number + 1;
		missions = new LinkedList<>();
		for (int i = 0; i < missionArray.size(); i++) {
			JsonObject mission = missionArray.get(i).getAsJsonObject();
			MissionInfo missionInfo = new MissionInfo();
			LinkedList<String> agentsSerialNumbers = jsonArrayToList(mission.get("serialAgentsNumbers").getAsJsonArray());
			missionInfo.setSerialAgentsNumbers(agentsSerialNumbers);
			missionInfo.setDuration(mission.get("duration").getAsInt());
			missionInfo.setGadget(mission.get("gadget").getAsString());
			missionInfo.setMissionName(mission.get("name").getAsString());
			missionInfo.setTimeExpired(mission.get("timeExpired").getAsInt());
			missionInfo.setTimeIssued(mission.get("timeIssued").getAsInt());
			missions.add(missionInfo);
		}
		listToHashMap(missions);
	}

	@Override
	protected void initialize() {
		System.out.println("intelligence " + number + ": initialized");
		subscribeBroadcast(TickBroadcast.class, event ->{
			System.out.println("intelligence " + number + ": TickBroadcast " + event.getCurrentTick());
			if(missionsHashMap.containsKey(event.getCurrentTick())){
				for(MissionInfo mission : missionsHashMap.get(event.getCurrentTick())){
					System.out.println("intelligence " + number + ": send event ");
					getSimplePublisher().sendEvent(new MissionReceivedEvent(mission, event.getCurrentTick()));
				}
			}
		});
		subscribeBroadcast(TimeIsUp.class, event ->{
			terminate();
		});
	}

	private LinkedList<String> jsonArrayToList (JsonArray array) {
		LinkedList<String> list = new LinkedList<>();
		for (int i = 0; i < array.size(); i++){
			list.add(array.get(i).getAsString());
		}
		return list;
	}

	private void listToHashMap (LinkedList<MissionInfo> list) {
		for (MissionInfo mission : list ){
			missionsHashMap.putIfAbsent(mission.getTimeIssued(), new LinkedList<MissionInfo>());
			missionsHashMap.get(mission.getTimeIssued()).add(mission);
		}
	}



}
