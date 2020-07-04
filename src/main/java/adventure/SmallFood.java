package adventure;

import org.json.simple.JSONObject;

public class SmallFood extends Food implements Tossable{

    public SmallFood(){

    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public SmallFood(JSONObject itemObj){
        super(itemObj);
    }


    /**
     * Signifies user tossing item
     * @return prompt to user indicating item been tossed
     */
    @Override
    public String toss(){
        return "THE " + super.getName().toUpperCase() + " HAS BEEN TOSSED.";
    }
}
