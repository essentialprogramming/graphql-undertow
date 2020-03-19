package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Filter {

    private List<String> tags;
    private String firstName;
    private String lastName;
    private String startDate;
    private String endDate;
    private String title;

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
