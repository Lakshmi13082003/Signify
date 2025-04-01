package com.example.imagepro.ADMIN;

import java.sql.Struct;

public class DataClass {

    private String imageUrl, caption;
    private String subject;
    private String key;


    public DataClass()
    {

    }



    public DataClass(String imageUrl, String caption , String subject )
    {
        this.imageUrl=imageUrl;
        this.caption=caption;
        this.subject=subject;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
