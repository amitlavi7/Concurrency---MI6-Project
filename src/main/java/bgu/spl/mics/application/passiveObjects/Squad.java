package bgu.spl.mics.application.passiveObjects;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private Map<String, Agent> agents;
	private static  class SquadHolder {
		private static Squad instance = new Squad();
	}

	private Squad(){
		agents = new HashMap<String, Agent>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		return SquadHolder.instance;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {
		for (Agent agent : agents) {
			this.agents.put(agent.getSerialNumber(), agent);
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		synchronized (this) { // dont think that we need to synchronize
			if (serials == null){
				for (String serial : agents.keySet()){
					System.out.println("releasing agent because of null" + agents.get(serial).getSerialNumber());
					agents.get(serial).release();
					System.out.println("agent " + agents.get(serial).getSerialNumber() + " availability: " + agents.get(serial).isAvailable());
				}
			}
			else {
				for (String serial : serials) {
					if (agents.containsKey(serial))
						agents.get(serial).release();
				}
				System.out.println("releasing agents");
			}
			System.out.println("wake threads");
			notifyAll();
		}


	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   milliseconds to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		for(String serial : serials){
			agents.get(serial).acquire();
		}
		try{
			System.out.println(Thread.currentThread().getName() + " is going to sleep for " + time + " ticks");
			Thread.sleep(time*100);
			System.out.println(Thread.currentThread().getName() + " is waking up");
		}
		catch(Exception e) {

		}
//		for (String serial: serials){
//			if (agents.containsKey(serial))
//				agents.get(serial).release();
//		}


	}

	/**
	 * acquires an agent, i.e. holds the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public boolean getAgents(List<String> serials){
			for (String serial : serials) {
				if (!agents.containsKey(serial))
					return false;
			}
		synchronized (this) {
			for (String serial : serials) {
				if (!agents.get(serial).isAvailable()) {
					try {
						System.out.println("agent " + agents.get(serial).getSerialNumber() + " availability: " + agents.get(serial).isAvailable());
						wait();
					} catch (Exception ignored) {
					}
				}
			}
			for (String serial : serials) {
				agents.get(serial).acquire();
				System.out.println("acquire: agent " + agents.get(serial).getSerialNumber() + " availability: " + agents.get(serial).isAvailable());
			}
		}
		return true;
	}

    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
    	List<String> nameList = new LinkedList<>();
		for (String serial: serials){
			if (agents.containsKey(serial))
				nameList.add(agents.get(serial).getName());
		}
		return nameList;
    }

}
