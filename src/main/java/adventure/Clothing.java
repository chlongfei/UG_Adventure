package adventure;

import org.json.simple.JSONObject;

public class Clothing extends Item implements Wearable{
    private boolean wearing;

    public Clothing(){
        wearing = false;
    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public Clothing(JSONObject itemObj){
        super(itemObj);
    }

    /**
     * Signals user intention to wear clothing
     * @return prompt notifying user they're now wearing the clothing
     */
    @Override
    public String wear(){
        wearing = true;
        return "YOU'RE NOW WEARING THE " + super.getName().toUpperCase() + ".";
    }

    /**
     * @return boolean indicating user is wearing the clothing
     */
    @Override
    public boolean isWear(){
        return wearing;
    }
    
}
