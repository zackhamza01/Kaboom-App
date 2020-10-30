package persistence;

import org.json.JSONObject;

// This class is using similar formatting to the JsonSerializationDemo on Github

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
