package nippenco.com;

import org.json.JSONObject;

import java.util.ArrayList;

import nippenco.com.api_model.Alert;
import nippenco.com.api_model.get_all_alarms.Datum;
import nippenco.com.api_model.login.Login;

/**
 * Created by aishwarydhare on 18/02/18.
 */

public class Common {
    private static final Common ourInstance = new Common();
    public JSONObject main_obj;

    // login response
    public Login login_datum;
    public int selected_login_device;
    public ArrayList<Alert> alerts_arr;
    public String device_history_for = "";
    public int device_history_for_pos = -1;
    public Datum alarm_to_edit;
    public int alarm_to_edit_pos;
    public NotificationArrivedInterface onNotificationArrivedCallback;

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }
}
