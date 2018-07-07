package nippenco.com.api_model;

import org.json.JSONObject;

/**
 * Created by aishwarydhare on 10/03/18.
 */

public class CloudMessage {

    public String alarm_name;
    public int id;
    public double feed_value;
    public String device_name;
    public int device_id;
    public double condition_value;
    public String created_at;
    public String description;
    public String condition_name;

    public CloudMessage(String alarm_name, int id, double feed_value, String device_name, int device_id, double condition_value, String created_at, String description, String condition_name) {
        this.alarm_name = alarm_name;
        this.id = id;
        this.feed_value = feed_value;
        this.device_id = device_id;
        this.device_name = device_name;
        this.condition_value = condition_value;
        this.created_at = created_at;
        this.description = description;
        this.condition_name = condition_name;
    }

}
