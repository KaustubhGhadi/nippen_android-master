
package nippenco.com.api_model.get_all_alarms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parameter {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("data_type")
    @Expose
    private String dataType;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Parameter() {
    }

    /**
     * 
     * @param id
     * @param dataType
     * @param name
     * @param key
     */
    public Parameter(String key, String name, Integer id, String dataType) {
        super();
        this.key = key;
        this.name = name;
        this.id = id;
        this.dataType = dataType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
