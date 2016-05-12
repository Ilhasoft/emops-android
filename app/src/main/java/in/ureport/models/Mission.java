package in.ureport.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by john-mac on 5/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mission {

    private String key;

    private String name;

    public Mission() {
    }

    public Mission(String key) {
        this.key = key;
    }

    public Mission(String key, String name) {
        this.key = key;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }
}
