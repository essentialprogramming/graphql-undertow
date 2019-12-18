package com.model;

import com.api.resources.LocalDateDeserializer;
import com.api.resources.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.util.List;

public class SearchCriteria {

    private String titleContains;
    private List<String> tagsContains;
    private String authorFirstName;
    private String authorLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private String articleId;

    @JsonProperty("title_contains")
    public String getTitleContains() {
        return titleContains;
    }

    @JsonProperty("tags_contains")
    public List<String> getTagsContains() {
        return tagsContains;
    }

    @JsonProperty("first_name")
    public String getAuthorFirstName() {
        return authorFirstName;
    }

    @JsonProperty("last_name")
    public String getAuthorLastName() {
        return authorLastName;
    }

    @JsonProperty("date")
    public LocalDate getDate() {
        return date;
    }

    @JsonProperty("article_id")
    public String getArticleId() {
        return articleId;
    }
}
