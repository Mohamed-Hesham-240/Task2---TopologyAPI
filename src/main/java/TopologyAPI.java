// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * The class used to provide the topologies required APIS
 */
public class TopologyAPI {
    private static final ArrayList<Topology> topologies = new ArrayList<>();

    /**
     * A private constructor to prevent instantiation of TopologyAPI
     */
    private TopologyAPI(){
        //Class can not be instantiated.
    }

    /**
     * Search for a certain topology in the memory if exists
     * @param topologyID - The unique id of the topology to be found
     * @return the required topology if it exists, otherwise null
     */
    private static Topology findTopologyById(String topologyID){
        for (Topology topology : topologies){
            if (topologyID.equals(topology.getId())) {
                return topology;
            }
        }
        return null;
    }

    /**
     * Read a topology from a JSON file and store it in an ArrayList
     * @param path - The exact location of the topology file to be read
     * @return The topology read from the file and stored in the memory
     */
    public static Topology readJson(String path){
        String jsonString = Utility.readFile(path);
        JsonObject jsonObject = Utility.parseJson(jsonString);
        Topology topology = new Topology((jsonObject));
        topologies.add(topology);
        return topology;
    }

    /**
     * Write the information of a topology in file existing or created at a certain location
     * @param topologyID - The unique id of the topology to be written into the file
     * @param path - The exact location of the file to write into
     */
    public static void writeJson(String topologyID, String path){
        Topology topology = findTopologyById(topologyID);
        String topologyString = "";
        if (topology != null){
            //topologyString = topology.toString();
            topologyString = Utility.formatJson(topology.getInformation());
        }
        Utility.writeToFile(topologyString, path);
    }

    /**
     * Give the list of all topologies stored in the memory
     * @return An ArrayList containing all topologies stored in the memory
     */
    public static ArrayList<Topology> queryTopologies(){
        return topologies;
    }

    /**
     * Delete a given topology from memory if exists
     * @param topologyID - The unique id of the topology to be deleted from memory
     * @return True if the topology is found and deleted, or false if the topology does not exist in memory
     */
    public static boolean deleteTopology (String topologyID){
        Topology topology = findTopologyById(topologyID);
        if (topology != null){
            topologies.remove(topology);
            return true;
        }
        return false;
    }

    /**
     * Find devices contained in a topology if the topology exists
     * @param topologyID - The unique id of the topology to query its devices
     * @return An ArrayList of the devices contained in the topology, or null if the topology does not exist in memory
     */
    public static ArrayList<Device> queryDevices (String topologyID){
        Topology topology = findTopologyById(topologyID);
        if (topology == null)
            return null;
        else{
            return topology.getDevices();
        }
    }

    /**
     * Find all devices in a topology connected to the same netlist node
     * @param topologyID - The unique id of the topology containing the devices to search for
     * @param netListID - The id of the netlist node to find the devices connected to it
     * @return An ArrayList of all devices connected to the node in the topology
     */
    public static ArrayList<Device>queryDevicesWithNetListNode (String topologyID, String netListID){
        //first find the required topology
        ArrayList<Device> matchingDevices = null;
        Topology topology = findTopologyById(topologyID);
        if (topology != null) {
            matchingDevices = new ArrayList<>();
            for (Device device : topology.getDevices()) {
                if (device.hasNetList(netListID))
                    matchingDevices.add(device);
            }
        }
        return matchingDevices;
    }

}
