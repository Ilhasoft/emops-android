package in.ureport.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by john-mac on 5/11/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mission {

    @JsonIgnore
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mission mission = (Mission) o;
        return key.equals(mission.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
