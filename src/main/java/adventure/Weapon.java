package adventure;

import org.json.simple.JSONObject;

public class Weapon extends Item implements Tossable {

    public Weapon(){

    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public Weapon(JSONObject itemObj){
        super(itemObj);
    }

    /**
     * Signifies user tossing item
     * @return prompt to user notifying item has been tossed
     */
    @Override
    public String toss(){
        return "THE " + super.getName().toUpperCase() + " HAS BEEN TOSSED.";
    }
}
