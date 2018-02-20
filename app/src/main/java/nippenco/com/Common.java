package nippenco.com;

import org.json.JSONObject;

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

    public static Common getInstance() {
        return ourInstance;
    }

    private Common() {
    }
}
