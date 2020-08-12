package com.roshanaryal.quizzerjava;

public class CategoriesModal {

    private String name;
    private int sets;
    private String url;

    public  CategoriesModal(){}


    public CategoriesModal( String name, int sets,String url) {
        this.url = url;
        this.name = name;
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
