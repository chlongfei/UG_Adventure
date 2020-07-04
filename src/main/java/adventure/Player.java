package adventure;

import java.util.ArrayList;

public class Player implements java.io.Serializable{

    private static final long serialVersionUID = -2703711678806403698L;
    private String name;
    private ArrayList<Item> inventory;
    private Room currentRoom;
    private String gameSaveName;

    /**
     * Initialize player information
     */
    public Player(){
        name = null;
        inventory = new ArrayList<Item>(); //creates an empty inventory
        currentRoom = null;
        gameSaveName = null;
    }

    /**
     * Initializes player information rfom parameters
     * @param pname player name
     * @param curRoom current room player is in
     */
    public Player(String pname, Room curRoom){
        this();
        setName(pname);
        this.currentRoom = curRoom;
    }

    /**
     * Sets player name
     * @param pName player name
     */
    public void setName(String pName){
        this.name = pName;
    }

    /**
     * Reterives the name of the player
     * @return player name
     */
    public String getName(){
        return name;
    }

    /**
     * Adds an item to player inventory
     * @param itemToAdd item to add to player inventory
     */
    public void addInventory(Item itemToAdd){
        this.inventory.add(itemToAdd);
    }

    /**
     * Reterives the players inventory
     * @return arraylist of players inventory
     */
    public ArrayList<Item> getInventory(){
        return inventory;
    }


    /**
     * Reterives the item from  player inventory
     * @param  itemName of item to retrieved
     * @return item retrieved
     */
    public Item getItem(String itemName){
        for (Item eachItem : this.inventory) {
            if (eachItem.getName().equals(itemName.toLowerCase())) {
                return eachItem;
            }
        }
        return null;
    }

    /**
     * Removes item from player inventory
     * @param itemName name of item to remove
     */
    public void removeItem(String itemName){
        this.inventory.remove(getItem(itemName));
    }

    /**
     * Answers queries for if player has an item
     * @param itemName item to check for
     * @return boolean indicating if player has item
     */
    public boolean doYouHave(String itemName){
        for (Item eachItem : this.inventory){
            if(eachItem.getName().equals(itemName.toLowerCase())){
                return true;
            }
        }
        return false;
    }
    /**
     * Sets current room that player is in
     * @param curRoom current room player is in
     */
    public void setCurrentRoom(Room curRoom){
        this.currentRoom = curRoom;
    }

    /**
     * Reterives the current room that the player is in
     * @return room player is in
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    /**
     * Sets the name of the game save file
     * @param saveName name of game save
     */
    public void setSaveGameName(String saveName){
        this.gameSaveName = saveName;
    }
    
    /**
     * Reterives the name of the game file for players game session
     * @return name of game file save
     */
    public String getSaveGameName(){
        return gameSaveName;
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Player{"
                + "name='" + name + '\''
                + ", inventory=" + inventory
                + ", currentRoom=" + currentRoom
                + ", gameSaveName='" + gameSaveName + '\''
                + '}';
    }

}

