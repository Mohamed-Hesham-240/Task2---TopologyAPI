// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
import com.google.gson.JsonObject;

import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class representing devices contained in topologies
 */
public class Device {
    private final String type;
    private final String id;
    private final JsonObject netList;
    private final JsonObject information;

    /**
     * Device constructor to initialize its data members
     * @param information - The JsonObject containing the device information
     */
    public Device(JsonObject information){
        this.information = information;
        this.type = information.get("type").getAsString();
        this.id = information.get("id").getAsString();
        this.netList = information.get("netlist").getAsJsonObject();
    }

    /**
     * type data member getter
     * @return A String representing the type of the device
     */
    public String getType() {
        return this.type;
    }

    /**
     * id data member getter
     * @return A String representing the id of the device
     */
    public String getID() {
        return this.id;
    }

    /**
     * netList data member getter
     * @return A JsonObject representing the netlist of the device
     */
    public JsonObject getNetList() {
        return this.netList;
    }

    /**
     * information data member getter
     * @return A JsonObject representing the information of the device
     */
    public JsonObject getInformation() {
        return this.information;
    }

    /**
     * Determines whether the device is connected to a certain netlist node or not
     * @param netListID - The netlist node to check to the device's connectivity to it
     * @return True if device is connected to the node, or false if not
     */
    public boolean hasNetList(String netListID){
        String sNetList = this.netList.toString();
        Pattern pattern = Pattern.compile(":\"" + netListID + "\"");
        Matcher matcher = pattern.matcher(sNetList);
        return matcher.find();
    }

    /**
     * Give the string representation of the device
     * @return A string containing the device's information
     */
    @Override
    public String toString() {
        return this.information.toString();
    }

}
