package com.example.develop.model;

public class ObjectClicked {
    private String id;
    private static ObjectClicked  objectClicked= null;

    public static ObjectClicked getObjectClicked(){
        if(objectClicked == null){
            objectClicked = new ObjectClicked();
        }
        return objectClicked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
