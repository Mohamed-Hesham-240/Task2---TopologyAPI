// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.*;
import java.util.Scanner;

/**
 * A class used to provide helper methods to the TopologyAPI
 */
public class Utility {

    /**
     * A private constructor to prevent instantiation of Utility
     */
    private Utility(){
        //Class can not be instantiated.
    }

    /**
     * Parse a JSON String representing the topology into a JsonObject
     * @param json - A JSON string to be parsed into a JsonObject data type
     * @return JsonObject instance of the parsed JSON
     */
    public static JsonObject parseJson(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = new Gson();
        Object jsonMap ;
        JsonObject jsonObject = null;
        try {
            jsonMap = gsonBuilder.create().fromJson(json, Object.class);
            jsonObject =  gson.toJsonTree(jsonMap).getAsJsonObject();
        }
        catch (Exception e){
            System.out.println("Error parsing JSON.");
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Read the contents of a file into a string
     * @param path - The exact location of the file to be read
     * @return a string representing the contents of the file
     */
    public static String  readFile(String path){
        File jsonFile = new File(path);
        StringBuilder json = new StringBuilder();
        try {
            Scanner scanner = new Scanner(jsonFile);
            while(scanner.hasNextLine()){
                json.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * Create a file in the specified location if the file does not already exist
     * @param path - The exact location of the file to be created
     */
    private static void createFile(String path){
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Error creating output file.");
            e.printStackTrace();
        }
    }

    /**
     * Write data into a file given its location
     * @param data - The data to be written into the file
     * @param path - The exact location of the file to write the data into
     */
    public static void writeToFile(String data, String path){
        createFile(path);
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(data);
            myWriter.close();

        } catch (IOException e) {
            System.out.println("Error writing to the specified path");
            e.printStackTrace();
        }
    }

    /**
     * Format an unformatted JSON string by adding newlines and proper indentation
     * @param unformattedJson - An unformatted JSON string without newlines
     * @return a formatted version of the JSON string with newlines and proper indentation
     */
    public static String formatJson(JsonObject unformattedJson){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(unformattedJson);
    }

}
