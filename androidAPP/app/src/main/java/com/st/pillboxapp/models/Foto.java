package com.st.pillboxapp.models;

public class Foto {

    private String url;

    public Foto (){}

    public Foto(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "Foto{" +
                "url='" + url + '\'' +
                '}';
    }
}
