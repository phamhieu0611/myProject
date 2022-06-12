package com.pthieu.pls;

public class ImageUp{
    String title;
    String description;
    String image;
    String search;

    public ImageUp() {
    }

    public ImageUp(String title, String description, String image, String search) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.search = search;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getSearch() {
        return search;
    }
}
