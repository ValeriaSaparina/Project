package com.example.user.myapplication.API;

import java.util.List;

public class EventsCategoriesApiResponse {
    private List<EventCategoryIncludeApiResponse> values;

    public List<EventCategoryIncludeApiResponse> getValues() {
        return values;
    }

    public void setValues(List<EventCategoryIncludeApiResponse> values) {
        this.values = values;
    }
}
