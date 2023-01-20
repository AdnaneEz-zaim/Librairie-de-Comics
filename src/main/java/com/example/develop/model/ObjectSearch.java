package com.example.develop.model;

public class ObjectSearch {
    private String domain;
    private String search;
    private static ObjectSearch objectSearch= null;


    public static ObjectSearch getObjectSearch(){
        if(objectSearch == null){
            objectSearch = new ObjectSearch();
        }
        return objectSearch;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
