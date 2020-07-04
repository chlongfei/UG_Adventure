package adventure;

import org.json.simple.JSONObject;

public class Food extends Item implements Edible {

    public Food(){

    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public Food(JSONObject itemObj){
        super(itemObj);
    }

    /**
     * Signals user intention to eat
     * @return prompt to user notifying item has been consumed
     */
    @Override
    public String eat(){
        return "THE " + super.getName().toUpperCase() + " HAS BEEN CONSUMED.";
    }
    
}
