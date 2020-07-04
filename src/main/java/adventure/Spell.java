package adventure;

import org.json.simple.JSONObject;

public class Spell extends Item implements Readable {

    public Spell(){

    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public Spell(JSONObject itemObj){
        super(itemObj);
    }

    /**
     * @return placeholder string signifying user reading text
     */
    @Override
    public String read(){
        return "(some writing in a foreign language unknown)";
    }
}
