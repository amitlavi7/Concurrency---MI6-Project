package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Squad;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonObject;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1){
            System.out.println("Invalid args size");
            return;
        }
        //TODO change the json reader
        Gson gson = new Gson();
        JsonObject obj = (JsonObject) new JsonParser().parse(new FileReader(args[0]));
        JsonArray inventory = obj.getAsJsonArray("inventory");
        JsonArray squad =  obj.getAsJsonArray("squad");
        JsonArray intelligence = obj.getAsJsonObject("services").getAsJsonArray("intelligence");
        loadInventory(inventory);
        loadSquad(squad);
    }

    private static void loadInventory(JsonArray inventory) {
        Inventory inv = Inventory.getInstance();
        String[] itemsToLoad = new String[inventory.size()];
        for (int i = 0; i < inventory.size(); i++)
            itemsToLoad[i] = inventory.get(i).toString();
        inv.load(itemsToLoad);
    }

    private static void loadSquad(JsonArray squad) {
        Squad s = Squad.getInstance();
        Agent[] AgentsToLoad = new Agent[squad.size()];
        Agent agent = new Agent();
        for (int i = 0; i < squad.size(); i++) {
            agent.setName(((JsonObject)squad.get(i)).get("name").toString());
            agent.setSerialNumber(((JsonObject)squad.get(i)).get("serialNumber").toString());
            AgentsToLoad[i] = agent;
        }
        s.load(AgentsToLoad);
    }

    private static void loadMissonsToIntelligence(JsonArray intelligence){
        MissionInfo info = new MissionInfo();


    }
}
