package com.example.q3z;

import java.util.ArrayList;

public class GameInfo {
    public String name;
    public String author;
    public String genere;
    public String directory;

    public GameInfo(String name, String author, String genere, String directory) {
        this.name = name;
        this.author = author;
        this.genere = genere;
        this.directory= directory;
    }


    public  ArrayList<String> GetInfo(){
        ArrayList<String> info= new ArrayList<String>();
        info.add(name);
        info.add(author);
        return  info;
    }
}
