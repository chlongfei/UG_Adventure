package adventure;

import org.json.simple.JSONObject;

public class BrandedClothing extends Clothing implements Readable{

    private boolean wearing;

    public BrandedClothing(){
        wearing = false;
    }

    /**
     * Initializes the item information
     * @param itemObj JSONObject of the item
     */
    public BrandedClothing(JSONObject itemObj){
        super(itemObj);
    }


    /**
     * @return the place holder for readable content
     */
    @Override
    public String read(){
        return "(Some hip brand)";
    }
    
}
