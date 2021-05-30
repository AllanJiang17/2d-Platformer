package Persistence;

import org.json.JSONObject;

public interface Savable {
    //EFFECTS: return the object as a JSON object
    JSONObject toJson();
}
