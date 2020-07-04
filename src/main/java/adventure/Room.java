package adventure;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Room implements java.io.Serializable{
    private static final long serialVersionUID = -8576938297504176481L;
    /* you will need to add some private member variables */
    private int id;
    private boolean start;
    private String name;
    private String shortDescription;
    private String longDescription;
    private Map<String,Integer> enterence;
    private Map<String,Item> loot;
    private boolean hasItems;
    private Adventure currentAdventure;

    /**
     * Initializes the room values with a default values
     */
    public Room(){
        id = 0;
        start = false;
        name = "";
        shortDescription = "";
        longDescription = "";
        enterence = new HashMap<String,Integer>();
        loot = new HashMap<String, Item>();
        hasItems = false;
        currentAdventure = null;
    }

    /**
     * Constructor initializes instance veriable with adventure information
     * @param roomObj the JSONObject of current room
     * @param curAdventure referenec to current adventure instance
     */
    public Room(JSONObject roomObj, Adventure curAdventure){
        this();
        setCurrentAdventure(curAdventure);
        setRoomInformation(roomObj);
        setEnterence((JSONArray)roomObj.get("entrance")); //set the enterences in this room

        if (roomObj.containsKey("loot")){ //if loot is present...
            hasItems = true;
            setLoot((JSONArray)roomObj.get("loot")); //...add the list of loot
        }
    }

    /**
     * Initializes room with descriptive information
     * @param roomObj
     */
    public void setRoomInformation(JSONObject roomObj){
        setId(Math.toIntExact((long)roomObj.get("id"))); //set room id
        setName((String)roomObj.get("name"));//set room name
        if (roomObj.containsKey("start")){ //determine if parameter start is in JSON
            setStart((String)roomObj.get("start")); //set room as starting room for game
        }
        setShortDescription((String)roomObj.get("short_description")); //set the rooms short description
        setLongDescription(roomObj.get("long_description").toString()); //set the long description of the room
    }

    /**
     * Sets room id
     * @param value room id
     */
    public void setId(int value){
        id = value;
    }

    /**
     * Sets room name
     * @param value room name
     */
    public void setName(String value){
        name = value;
    }

    /**
     * Sets room as starting room of the game
     * @param value if the room is starting point (true) or not (false)
     */
    public void setStart(String value){
        if (value.equals("true")){
            start = true;
            currentAdventure.setCurrentRoomId(getId());
        }else{
            start = false;
        }
    }

    /**
     * Sets room short description.
     * @param value string discription.
     */
    public void setShortDescription(String value){
        shortDescription = value;
    }

    /**
     * Set room long description.
     * @param value string description.
     */
    public void setLongDescription(String value){
        longDescription = value;
    }

    /**
     * Sets room entrences.
     * @param doorArr JSONArray of room enterences.
     */
    public void setEnterence(JSONArray doorArr){
            for (Object currentDoor : doorArr) {
           JSONObject theDoor = (JSONObject) currentDoor;
           String doorDirection = (String) theDoor.get("dir");
           int doorId = Math.toIntExact((long) theDoor.get("id"));
           enterence.put(doorDirection.toUpperCase(), doorId);
       }
    }

    /**
     * @return map of all enterences in room
     */
    public Map<String,Integer> getEnterences(){
        return enterence;
    }

    /**
     * Sets the loot information of the room.
     * @param lootArr JSONArray of loot in the room.
     */
    public void setLoot(JSONArray lootArr){
       try{
           for (Object itemCnt:lootArr){
               JSONObject currentItem = (JSONObject) itemCnt;
               int itemId = Math.toIntExact((long)currentItem.get("id"));
               findMeLoot(itemId).setRoom(this);
               loot.put(findMeLoot(itemId).getName().toUpperCase(), findMeLoot(itemId));
           }
       }catch(NullPointerException npe){
           currentAdventure.reportError(true);
        }
    }

    /**
     * Adds item to room inventory
     * @param itemToAdd the item to be added to the room
     */
    public void addLoot(Item itemToAdd){
        loot.put(itemToAdd.getName().toUpperCase(), itemToAdd);
        if(!loot.isEmpty()){
            setHasItems(true);
        }
    }

    /**
     * Sets indication of if room has items
     * @param has boolean value indicating if contains any items
     */
    public void setHasItems(boolean has){
        this.hasItems = has;
    }

    /**
     * Sets current adventure instance
     * @param adventure adventure object
     */
    public void setCurrentAdventure(Adventure adventure){
        this.currentAdventure = adventure;
    }

    /**
     * Finds and returns the item by item id
     * @param lootId item id
     * @return item that corresponds to the item id
     */
    private Item findMeLoot(int lootId){
        ArrayList<Item> itemList = currentAdventure.listAllItems();
        Item foundItem = null;
        for (Item allItem:itemList){
            if (allItem.getId() == lootId){
                foundItem = allItem;
                break;
            }
        }
        return foundItem;
    }

    /**
     * Queries room inventory for item to seek.
     * @param lootId name of the item user wishes to seek
     * @return true if item exists, false if item does not exist
     */
    public boolean doYouHave(String lootId){
        return loot.containsKey(lootId.toUpperCase());
    }

    /**
     * Removes a loot item from the room inventory
     * @param itemToGo item to be removed
     */
    public void removeItem(Item itemToGo){
        loot.remove(itemToGo.getName().toUpperCase());
        if (loot.isEmpty()){
            this.hasItems = false;
        }
    }

    /**
     * Creates a list of all items present in this room
     * @return an arrayList of all items in this room
     */
    public ArrayList<Item> listItems(){
        //lists all the items in the room
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.addAll(loot.values());       
        return itemList;
    }

    /**
     * @return room id
     */
    public int getId(){
        return id;
    }

    /**
     * @return room name
     */
    public String getName(){
        return name;
    }

    /**
     * @return long description of room
     */
    public String getLongDescription(){
        return longDescription;
    }

    /**
     * @return short description of room
     */
    public String getShortDescription(){
        return shortDescription;
    }

    /**
     * Finds and returns the rooms connected to this room depeding on direction
     * user wishes to travel
     * @param direction user defined direction of travel
     * @return room in user defined direction
     */
    public Room getConnectedRoom(String direction) {
        if (enterence.containsKey(direction.toUpperCase())){
            currentAdventure.setCurrentRoomId(enterence.
                    get(direction.toUpperCase())); //sets curernt room to the new room
            return currentAdventure.getRoom(enterence.get(direction.toUpperCase())); //returns the new room
        }
        return null;
    }
    /**
     * Finds and returns the rooms connected to this room depeding on direction
     * user wishes to travel
     * @param direction user defined direction of travel
     * @param valid integer value to initiate testing version of method(overloading)
     * @return room in user defined direction
     */
    public Room getConnectedRoom(String direction, int valid) {
        if (enterence.containsKey(direction.toUpperCase())){
            return currentAdventure.getRoom(enterence.get(direction.toUpperCase())); //returns the new room
        }
        return null;
    }

    /**
     * Indicates if this room has any items present
     * @return if room has (true) items or not (false)
     */
    public boolean hasLoot(){
        return hasItems;
    }

    /**
     * Finds and returns the item present in this room depending
     * on the item name
     * @param lootName item name
     * @return item requested for
     */
    public Item getLoot(String lootName){
        return loot.get(lootName);
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Room{" + "id=" + id
                + ", start=" + start + ", name='" + name + '\''
                + ", shortDescription='" + shortDescription + '\''
                + ", longDescription='" + longDescription + '\''
                + ", enterence=" + enterence
                + ", loot=" + loot + "hasItems=" + hasItems
                + ", currentAdventure=" + currentAdventure + '}';
    }
}
