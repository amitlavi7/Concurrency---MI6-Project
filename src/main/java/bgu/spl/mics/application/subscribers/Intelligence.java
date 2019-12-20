package bgu.spl.mics.application.subscribers;

//import bgu.spl.mics.Publisher;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.TickBroadcast;
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
	private HashMap<Integer, MissionInfo> missionsHashMap;
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
			missionInfo.setGadget(mission.get("gadget").toString());
			missionInfo.setMissionName(mission.get("missionName").toString());
			missionInfo.setTimeExpired(mission.get("timeExpired").getAsInt());
			missionInfo.setTimeIssued(mission.get("timeIssued").getAsInt());
			missions.add(missionInfo);
		}
		listToHashMap(missions);
	}

	@Override
	protected void initialize() {
		subscribeBroadcast(TickBroadcast.class, event ->{

		});
	}

	private LinkedList<String> jsonArrayToList (JsonArray array) {
		LinkedList<String> list = new LinkedList<>();
		for (int i = 0; i < array.size(); i++){
			list.add(array.get(i).toString());
		}
		return list;
	}

	private void listToHashMap (LinkedList<MissionInfo> list) {
		for (MissionInfo mission : list ){
			missionsHashMap.put(mission.getTimeIssued(), mission);
		}
	}



}
