
package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("devices")
    @Expose
    private Devices devices;
    @SerializedName("recent_alerts")
    @Expose
    private List<RecentAlert> recentAlerts = null;
    @SerializedName("user_info")
    @Expose
    private UserInfo userInfo;
    @SerializedName("unread_alerts")
    @Expose
    private Integer unreadAlerts;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param unreadAlerts
     * @param userInfo
     * @param devices
     * @param recentAlerts
     */
    public Data(Devices devices, List<RecentAlert> recentAlerts, UserInfo userInfo, Integer unreadAlerts) {
        super();
        this.devices = devices;
        this.recentAlerts = recentAlerts;
        this.userInfo = userInfo;
        this.unreadAlerts = unreadAlerts;
    }

    public Devices getDevices() {
        return devices;
    }

    public void setDevices(Devices devices) {
        this.devices = devices;
    }

    public List<RecentAlert> getRecentAlerts() {
        return recentAlerts;
    }

    public void setRecentAlerts(List<RecentAlert> recentAlerts) {
        this.recentAlerts = recentAlerts;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getUnreadAlerts() {
        return unreadAlerts;
    }

    public void setUnreadAlerts(Integer unreadAlerts) {
        this.unreadAlerts = unreadAlerts;
    }

}
