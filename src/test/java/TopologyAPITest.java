// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class TopologyAPITest {

    /**
     * Reading a valid json file
     * Expected: String representation of the topology / file
     */
    @Test
    public void readJson1() {
        TopologyAPI.deleteTopology("top1");
        String pathInput = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        Topology topology = TopologyAPI.readJson(pathInput);
        String expected = "{\"id\":\"top1\",\"components\":[{\"type\":\"resistor\",\"id\":\"res1\",\"resistance\":{\"default\":100.0,\"min\":10.0,\"max\":1000.0},\"netlist\":{\"t1\":\"vdd\",\"t2\":\"n1\"}},{\"type\":\"nmos\",\"id\":\"m1\",\"m(l)\":{\"default\":1.5,\"min\":1.0,\"max\":2.0},\"netlist\":{\"drain\":\"n1\",\"gate\":\"vin\",\"source\":\"vss\"}}]}";
        String found = topology.toString();
        assertEquals(expected,found);
    }

    /**
     * Reading an empty json file
     * Expected: Throw an exception
     */
    @Test (expected = Exception.class)
    public void readJson2() {
        TopologyAPI.deleteTopology("top1");
        String pathInput = "..\\Topology API\\src\\test\\resources\\test cases files\\invalid syntax1.json";
        TopologyAPI.readJson(pathInput);
    }

    /**
     * Reading from a non-existing file
     * Expected: Throw an exception
     */
    @Test (expected = Exception.class)
    public void readJson3() {
        TopologyAPI.deleteTopology("top1");
        String pathInput = "..\\Topology API\\src\\test\\resources\\test cases files\\invalid syntax.json";
        TopologyAPI.readJson(pathInput);
    }

    /**
     * Reading invalid json data
     * Expected: Throw an exception
     */
    @Test (expected = Exception.class)
    public void readJson4() {
        TopologyAPI.deleteTopology("top1");
        String pathInput = "..\\Topology API\\src\\test\\resources\\test cases files\\invalid syntax2.json";
        TopologyAPI.readJson(pathInput);
    }

    /**
     * Writing a topology found in memory
     * Expected: Writing is done successfully
     */
    @Test
    public void writeJson1() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json",
                outputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid output1.json";
        Topology expectedTopology = TopologyAPI.readJson(inputPath);
        TopologyAPI.writeJson("top1",outputPath );
        Topology foundTopology = TopologyAPI.readJson(outputPath);
        String found = foundTopology.toString(),
                expected = expectedTopology.toString();
        assertEquals(expected, found);
    }

    /**
     * Writing a topology not found in memory
     * Expected: Writes nothing in file
     */
    @Test
    public void writeJson2() {
        TopologyAPI.deleteTopology("top1");
        String outputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\invalid syntax1.json";
        TopologyAPI.writeJson("not an existing id",outputPath );
        String found = Utility.readFile(outputPath),
                expected = "";
        assertEquals(expected, found);
    }

    /**
     * Getting a list of topologies previously stored in memory
     * Expected: Non-empty list of the topologies
     */
    @Test
    public void queryTopologies1() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Topology> topologies = TopologyAPI.queryTopologies();
        String expected = "[{\"id\":\"top1\",\"components\":[{\"type\":\"resistor\",\"id\":\"res1\",\"resistance\":{\"default\":100.0,\"min\":10.0,\"max\":1000.0},\"netlist\":{\"t1\":\"vdd\",\"t2\":\"n1\"}},{\"type\":\"nmos\",\"id\":\"m1\",\"m(l)\":{\"default\":1.5,\"min\":1.0,\"max\":2.0},\"netlist\":{\"drain\":\"n1\",\"gate\":\"vin\",\"source\":\"vss\"}}]}]",
                found = topologies.toString();
        assertEquals(expected, found);
    }

    /**
     * No topologies stored in memory
     * Expected: An empty list
     */
    @Test
    public void queryTopologies2() {
        TopologyAPI.deleteTopology("top1");
        ArrayList<Topology> topologies = TopologyAPI.queryTopologies();
        String expected = "[]",
                found = topologies.toString();
        assertEquals(expected, found);
    }

    /**
     * Delete an existing topology in memory
     * Expected: Topology deleted successfully
     */
    @Test
    public void deleteTopology1() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        TopologyAPI.readJson(inputPath);
        boolean isTopologyDeleted = TopologyAPI.deleteTopology("top1");
        assertTrue(isTopologyDeleted);
    }

    /**
     * Trying to delete a topology that does not exist in memory
     * Expected: A flag indicating that the topology does not exist in memory
     */
    @Test
    public void deleteTopology2() {
        TopologyAPI.deleteTopology("top1");
        boolean isTopologyDeleted = TopologyAPI.deleteTopology("top0");
        assertFalse(isTopologyDeleted);
    }

    /**
     * Search for devices in a topology that exists in the memory containing devices
     * Expected: A list of the topology devices
     */
    @Test
    public void queryDevices1() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevices("top1");
        String expected = "[{\"type\":\"resistor\",\"id\":\"res1\",\"resistance\":{\"default\":100.0,\"min\":10.0,\"max\":1000.0},\"netlist\":{\"t1\":\"vdd\",\"t2\":\"n1\"}}, {\"type\":\"nmos\",\"id\":\"m1\",\"m(l)\":{\"default\":1.5,\"min\":1.0,\"max\":2.0},\"netlist\":{\"drain\":\"n1\",\"gate\":\"vin\",\"source\":\"vss\"}}]",
                found = devices.toString();
        assertEquals(expected, found);
    }

    /**
     * Search for devices in a topology that does not exist in memory
     * Expected: null value
     */
    @Test
    public void queryDevices2() {
        TopologyAPI.deleteTopology("top1");
        ArrayList<Device> devices = TopologyAPI.queryDevices("top0");
        assertNull(devices);
    }

    /**
     * Search for devices in a topology that exists in the memory but does not contain components array
     * Expected: An empty list
     */
    @Test
    public void queryDevices3() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax2.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevices("top1");
        String expected = "[]",
                found  = devices.toString();
        assertEquals(expected, found);
    }

    /**
     * Search for devices in a topology that exists in the memory but contains an empty components array
     * Expected: An empty list
     */
    @Test
    public void queryDevices4() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax3.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevices("top1");
        String expected = "[]",
                found  = devices.toString();
        assertEquals(expected, found);
    }


    /**
     * query using a topology id that does not exist in memory
     * Expected: a null value
     */
    @Test
    public void queryDevicesWithNetListNode1() {
        TopologyAPI.deleteTopology("top1");
        ArrayList<Device> devices = TopologyAPI.queryDevicesWithNetListNode("top0", "vin");
        assertNull(devices);
    }

    /**
     * query using a topology id exists in memory, but has no devices connected to the given node
     * Expected: An empty list
     */
    @Test
    public void queryDevicesWithNetListNode2() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevicesWithNetListNode("top1", "vout");
        String expected = "[]",
                found  = devices.toString();
        assertEquals(expected, found);
    }

    /**
     * query using a topology id exists in memory and has devices connected to the node
     * Expected: An empty list
     */
    @Test
    public void queryDevicesWithNetListNode3() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax1.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevicesWithNetListNode("top1", "vin");
        String expected = "[{\"type\":\"nmos\",\"id\":\"m1\",\"m(l)\":{\"default\":1.5,\"min\":1.0,\"max\":2.0},\"netlist\":{\"drain\":\"n1\",\"gate\":\"vin\",\"source\":\"vss\"}}]",
                found  = devices.toString();
        assertEquals(expected, found);
    }

    /**
     * query using a topology id exists in memory and has no components array
     * Expected: An empty list
     */
    @Test
    public void queryDevicesWithNetListNode4() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax2.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevicesWithNetListNode("top1", "vin");
        String expected = "[]",
                found  = devices.toString();
        assertEquals(expected, found);
    }

    /**
     * query using a topology id exists in memory and has an empty components array
     * Expected: An empty list
     */
    @Test
    public void queryDevicesWithNetListNode5() {
        TopologyAPI.deleteTopology("top1");
        String inputPath = "..\\Topology API\\src\\test\\resources\\test cases files\\valid syntax3.json";
        TopologyAPI.readJson(inputPath);
        ArrayList<Device> devices = TopologyAPI.queryDevicesWithNetListNode("top1", "vin");
        String expected = "[]",
                found  = devices.toString();
        assertEquals(expected, found);
    }
}