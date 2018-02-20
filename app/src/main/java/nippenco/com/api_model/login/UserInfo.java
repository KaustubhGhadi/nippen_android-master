
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;

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
    public UserInfo(String role, String lastName, String mobile, String firstName, String email, Integer id, String username) {
        super();
        this.role = role;
        this.lastName = lastName;
        this.mobile = mobile;
        this.firstName = firstName;
        this.email = email;
        this.id = id;
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
