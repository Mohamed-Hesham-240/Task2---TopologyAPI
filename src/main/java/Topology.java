// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * A class representing the topologies
 */
public class Topology {
    private final String id;
    private final JsonObject information;
    private final ArrayList<Device> devices;

    /**
     * Topology constructor to initialize its data members and extract the devices contained in the topology
     * @param information The JsonObject containing the topology information
     */
    public Topology(JsonObject information){
        this.information = information;
        this.id = information.get("id").getAsString();
        this.devices = new ArrayList<>();
        JsonElement componentsElement = information.get("components");
        if (componentsElement != null) {
            JsonArray components = componentsElement.getAsJsonArray();
            for (JsonElement component : components) {
                JsonObject deviceInformation = component.getAsJsonObject();
                this.devices.add(new Device(deviceInformation));
            }
        }
    }

    /**
     * id data member getter
     * @return A String representing the id of the device
     */
    public String getId(){
        return this.id;
    }

    /**
     * information data member getter
     * @return A JsonObject representing the information of the topology
     */
    public JsonObject getInformation(){
        return this.information;
    }

    /**
     * devices data member getter
     * @return An ArrayList of all the devices contained in the topology
     */
    public ArrayList<Device> getDevices() {
        return devices;
    }

    /**
     * Give the string representation of the topology
     * @return A string containing the topology's information
     */
    @Override
    public String toString(){
        return information.toString();
    }

}
