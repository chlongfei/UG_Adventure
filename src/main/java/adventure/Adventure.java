package adventure;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.StringWriter;
import java.util.Map;

public class Adventure implements java.io.Serializable{
    private static final long serialVersionUID = 5862334281429998136L;
    private JSONObject adventureJSON; //the original object parsed from the userdefined or default JSON file
    private HashMap<Integer,Room> roomMaster; //master list of all rooms in adventure
    private ArrayList<Item> itemMaster; //master list of all possible items in adventure
    private int currentRoomId;
    private boolean validJSON;
    private boolean errorItem;

    /**
     * Initializes variables with default values
     */
    public Adventure(){
        adventureJSON = null;
        roomMaster = null;
        itemMaster = null;
        currentRoomId = 0;
        validJSON = false;
        errorItem = false;
    }

    /**
     * Initializes the adventure class by setting a member variable to ref current adventures JSON file
     * @param theAdventure
     */
    public Adventure(JSONObject theAdventure){
        this();
        setAdventureJSON((JSONObject) theAdventure.get("adventure"));
        setItemsMaster(this.adventureJSON);
        setRoomMaster(this.adventureJSON);
    }

    /**
     * Validate JSON file for discrepancies that would inhibit proper execution
     * of program
     * @return string error message
     */
    public String validateJSON(){
        String result = "";
        if(errorItem){
            result += "[ERROR]: ITEM LIST DISCREPANCY IN JSON";
            setValidJSON(false);
        }
        if(!validateJSONRooms(new ArrayList<>(roomMaster.values()))){
            result += "[ERROR]: ROOM LIST DISCREPANCY IN JSON";
            setValidJSON(false);
        }
        return result;
    }


    /**
     * Called by internal methods to report any errors that may cause issues
     * inhibiting proper execution of program
     * @param err boolean value representing if error is present
     */
    public void reportError(boolean err){
        errorItem = err;
    }

    /**
     * Validates Room entries by JSON file
     * @param rooms arraylist of rooms in the adventure
     * @return boolean value representing if an error is present
     */
    public boolean validateJSONRooms(ArrayList<Room> rooms) {
       Map<String, Integer> roomEntrances = null;
       for (Room eachRoom : rooms) { //creates a mass connections list
           roomEntrances = eachRoom.getEnterences();
           if(!compareRoomConnection(eachRoom, eachRoom.getEnterences(), new ArrayList<>(roomEntrances.keySet()))){
               return false;
           }
       }
       return true;
    }

    /**
     * Compares the connecting rooms ensuring a connection is formed between any two rooms
     * as indicated by JSON
     * @param eaRm room being evaluated for connectivity
     * @param rmEntr map of entrances in room in question
     * @param direct list of directions that room should have a room connecting
     * @return boolean value representing if a room is not connected as intended
     */
    private boolean compareRoomConnection(Room eaRm,Map<String,Integer> rmEntr, ArrayList<String> direct){
        Room otherRoom = null;
        boolean pass = true;
        for (String dir : direct) {
            otherRoom = roomMaster.get(rmEntr.get(dir));
            pass = checkConnection(otherRoom, dir, eaRm);
            if(!pass){
                return false;
            }
        }
        return true;
    }

    /**
     * Helper function to compareRoomConnection() - checks for connectivity between two rooms
     * @param otherRoom destination room
     * @param dir direction of destination room
     * @param eaRm room in question
     * @return boolean value representing if room is not connected as intended
     */
    private boolean checkConnection(Room otherRoom, String dir, Room eaRm){
        boolean pass = true;
        try{
            if(otherRoom.getConnectedRoom(oppositeDirection(dir),1) == eaRm) {
                pass = true;
            } else{
                pass = false;
            }
        }catch (NullPointerException npe) {}
        return pass;
    }

    /**
     * @param dir direction
     * @return opposite to the specified direction
     */
    private String oppositeDirection(String dir){
        if(dir.equals("N")){
            return "S";
        }else if(dir.equals("S")){
            return "N";
        }else if(dir.equals("E")){
            return "W";
        }else if (dir.equals("W")){
            return "E";
        }
        return sendOppotiseUpDown(dir);
    }

    /**
     * @param dir direction
     * @return opposite to the specified direction
     */
    private String sendOppotiseUpDown(String dir){
        if (dir.equals("UP")){
            return "down";
        }else if (dir.equals("DOWN")){
            return "up";
        }
        return null;
    }

    /**
     * sets if JSON file is valid
     * @param value boolean parameter indicating validity
     */
    public void setValidJSON(boolean value){
        validJSON = value;
    }

    /**
     * Sets the adventure JSON
     * @param adventure adventure JSON
     */
    public void setAdventureJSON(JSONObject adventure){
        this.adventureJSON = adventure;
    }

    /**
     * Converts the JSONArray of rooms in this adventure into an ArrayList
     * @param theAdventure adventure json object
     */
    public void setRoomMaster(JSONObject theAdventure){
        JSONArray theRooms = (JSONArray)theAdventure.get("room");
        roomMaster = new HashMap<Integer,Room>();
        for (Object currentRoom:theRooms){
            JSONObject theRoom = (JSONObject) currentRoom;
            int roomId = Math.toIntExact((long)theRoom.get("id"));
            roomMaster.put(roomId,new Room((JSONObject)currentRoom,this));
        }
    }
    
    /**
     * Converts the JSONArray of items in this adventure into an ArrayList
     * @param theAdventure adventure json object
     */
    public void setItemsMaster(JSONObject theAdventure){
        JSONArray theItems = (JSONArray)theAdventure.get("item");
        itemMaster = new ArrayList<Item>();
        for(Object currentItem:theItems){
            JSONObject itemObj = (JSONObject) currentItem;
            createItem(itemObj);
        }
    }

        /* #Item subclasses
    (tfff)Food -> Item + Edible
    (ttff)SmallFood -> Food + Tossable
    (ffft)Clothing -> Item + Wearable
    (fftt)BrandedClothing -> Clothing + Readable
    (ftff)Weapon -> Item + Tossable
    (fftf)Spell -> Item + Readable
     */

    /**
     * Creates item of multiple types
     * @param theItems JSONObject for item
     */
    private void createItem(JSONObject theItems){
        String typeIdentifier = checkItemType(theItems);
        createFood(theItems,typeIdentifier);
        createSmFood(theItems,typeIdentifier);
        createClothing(theItems,typeIdentifier);
        createBrandedClothing(theItems,typeIdentifier);
        createWeapon(theItems,typeIdentifier);
        createSpell(theItems,typeIdentifier);
    }

    /**
     * Creates food item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createFood(JSONObject theItem, String typeId){
        if (typeId.equals("tfff")) {
            itemMaster.add(new Food(theItem));
        }
    }
    /**
     * Creates small food item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createSmFood(JSONObject theItem, String typeId){
        if (typeId.equals("ttff")) {
            itemMaster.add(new SmallFood(theItem));
        }
    }

    /**
     * Creates clothing item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createClothing(JSONObject theItem, String typeId){
        if (typeId.equals("ffft")) {
            itemMaster.add(new Clothing(theItem));
        }
    }

    /**
     * Creates branded clothing item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createBrandedClothing(JSONObject theItem, String typeId){
        if (typeId.equals("fftt")) {
            itemMaster.add(new BrandedClothing(theItem));
        }
    }

    /**
     * Creates weapon item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createWeapon(JSONObject theItem, String typeId){
        if (typeId.equals("ftff")) {
            itemMaster.add(new Weapon(theItem));
        }
    }

    /**
     * Creates spell item
     * @param theItem JSONObject for item
     * @param typeId string identifier to determine which item to create
     */
    private void createSpell(JSONObject theItem, String typeId){
        if (typeId.equals("fftf")) {
            itemMaster.add(new Spell(theItem));
        }
    }

    /**
     * Checks item type
     * @param theItems JSONObject for item
     * @return string type identifier
     */
    private String checkItemType(JSONObject theItems){
        String out = checkItemEdible(theItems) + checkItemTossable(theItems)
                    + checkItemReadable(theItems) + checkItemWearable(theItems);
        if(false) { //debug
            System.out.println("ITEM: " + theItems.get("name"));
            System.out.println("Type Patter: " + out);
            System.out.println("--------");
        }
        return out;
    }

    /**
     * Checks if item is edible
     * @param theItem JSONObject of the item
     * @return if item is edible or not
     */
    private String checkItemEdible(JSONObject theItem){
        try {
            if((boolean) theItem.get("edible")) {
                return "t";
            }
        }catch(NullPointerException e){}
        return "f";
    }

    /**
     * Checks if item is tossable
     * @param theItem JSONObject of the item
     * @return if item is tossable or not
     */
    private String checkItemTossable(JSONObject theItem){
        try{
            if((boolean)theItem.get("tossable")){
                return "t";
            }
        }catch(NullPointerException e){

        }
        return "f";
    }

    /**
     * Checks if item is readable
     * @param theItem JSONObject of the item
     * @return if item is readable or not
     */
    private String checkItemReadable(JSONObject theItem){
         try {
             if ((boolean) theItem.get("readable")) {
                 return "t";
             }
         }catch(NullPointerException e){}
        return "f";
    }

    /**
     * Checks if the item is wearable
     * @param theItem JSONObject of the item
     * @return if the item is wearable or not
     */
    private String checkItemWearable(JSONObject theItem){
        try{
            if ((boolean)theItem.get("wearable")){
                return "t";
            }
        }catch(NullPointerException e){}
        return "f";
    }

    /**
     * Sets the current room in use
     * @param id of the room in use
     */
    public void setCurrentRoomId(int id){
        currentRoomId = id;
    }

    /**
     * Fetches the room at id
     * @param id the room id
     * @return the room object
     */
    public Room getRoom(int id){
        return roomMaster.get(id);
    }


    /**
     * Creates a list of all the rooms present in this adventure
     * @return an ArrayList of rooms present in this adventure
     */
    public ArrayList<Room> listAllRooms(){
        ArrayList<Room> roomArr = new ArrayList<>();
        roomArr.addAll(roomMaster.values());
        return roomArr;
    }

    /**
     * Creates a list of all items present in this adventure
     * @return an ArrayList of items present in this adventure
     */
    public ArrayList<Item> listAllItems(){
        return itemMaster;
    }

    /**
     * Creates a description of the room
     * @return long description of the room
     */
    public String getCurrentRoomDescription(){
        StringWriter desc = new StringWriter();
        desc.write(roomMaster.get(currentRoomId).getLongDescription() + ".");
        return desc.toString();
    }
    
    /**
     * @return current room in use in the adventure
     */
    public Room getCurrentRoom(){
        return getRoom(currentRoomId);
    }
    /* you may wish to add additional methods*/

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Current adventure information:"
                + "\nPlayer current room = " + getCurrentRoom().getName()
                + "\nNumber of room in adventure = " + listAllRooms().size()
                + "\nNumber of items in adventure = " + listAllItems().size()
                + "\n";
    }
}
