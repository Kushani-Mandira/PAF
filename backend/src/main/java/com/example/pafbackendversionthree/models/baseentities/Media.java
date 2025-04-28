package com.example.pafbackendversionthree.models.baseentities;

public class Media {
    private String url;
    private String type;

    // Constructors
    public Media() {}

    public Media(String url, String type) {
        this.url = url;
        this.type = type;
    }

    // Getters and Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
