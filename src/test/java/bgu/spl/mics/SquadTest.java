package bgu.spl.mics;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class SquadTest {
    private Squad agents;
    private Agent[] agentsToLoad;

    @BeforeEach
    public void setUp(){
        agents = Squad.getInstance();
        Agent agent1 = new Agent();
        Agent agent2 = new Agent();
        Agent agent3 = new Agent();
        agent1.setSerialNumber("001");
        agent2.setSerialNumber("002");
        agent3.setSerialNumber("003");
        agent1.setName("James");
        agent2.setName("Shreder");
        agent3.setName("catZion");
        agentsToLoad = new Agent[]{agent1, agent2, agent3};
    }

    @Test
    public void test(){
        List <String> trueAgentsSerials = Arrays.asList("001", "002");
        List <String> falseAgentsSerials = Arrays.asList("001", "222");
        List <String> trueAgentsNames = Arrays.asList("James", "Shreder");
        List <String> falseAgentsNames = Arrays.asList("BB", "Gantz");
        assertNotNull(agents);
        agents.load(agentsToLoad);
        assertTrue(agents.getAgents(trueAgentsSerials));
        assertFalse(agents.getAgents(falseAgentsSerials));
        assertEquals(trueAgentsNames, agents.getAgentsNames(trueAgentsSerials));
        assertNotEquals(falseAgentsNames, agents.getAgentsNames(trueAgentsSerials));
        agents.releaseAgents(Arrays.asList("002"));
        assertFalse(agents.getAgents(Arrays.asList("002")));
    }
}
