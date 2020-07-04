package adventure;

import org.json.simple.JSONObject;

public class Item implements java.io.Serializable{
    private static final long serialVersionUID = 3717304587216265095L;
    /* you will need to add some private member variables */
    private int id;
    private String name;
    private String description;
    private Room parentRoom;

    /**
     * Initializes the item variables with default values
     */
    public Item(){
        id = 0;
        name = "";
        description = "";
        parentRoom = null;
    }    

    /**
     * Initializes the item with properties
     * @param itemObj JSON object of the item
     */
    public Item(JSONObject itemObj){
        this();
        setId(Math.toIntExact((long)itemObj.get("id")));
        setName((String)itemObj.get("name"));
        setDescription((String)itemObj.get("desc"));
    }

    /**
     * Establishes a connection between item and room
     * @param theRoom the room this item resides in
     */
    public void setRoom(Room theRoom){
        parentRoom = theRoom;
    }

    /**
     * Set item id
     * @param value item id
     */
    public void setId(int value){
        id = value;
    }

    /**
     * Set item name
     * @param value item name
     */
    public void setName(String value){
        name = value;
    }

    /**
     * Set item description
     * @param value item description
     */
    public void setDescription(String value){
        description = value;
    }

    /**
     * @return item id
     */
    public int getId(){
        return id;
    }

    /**
     * @return item name
     */
    public String getName(){
        return name;
    }

    /**
     * @return item description
     */
    public String getLongDescription(){

        return description;
    }

    /**
     * @return room this item belongs to
     */
    public Room getContainingRoom(){
        //returns a reference to the room that contains the item
        return parentRoom;
    }

    /**
     * @return an overview of all instance variables in class
     */
    @Override
    public String toString() {
        return "Item:"
                + "\nid=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", parentRoom=" + parentRoom;
    }

}
