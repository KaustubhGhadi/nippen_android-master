
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserInfo() {
    }

    /**
     * 
     * @param id
     * @param lastName
     * @param username
     * @param email
     * @param role
     * @param firstName
     * @param mobile
     */
    public UserInfo(String mobile, String firstName, String role, Integer id, String username, String lastName, String email) {
        super();
        this.mobile = mobile;
        this.firstName = firstName;
        this.role = role;
        this.id = id;
        this.username = username;
        this.lastName = lastName;
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
