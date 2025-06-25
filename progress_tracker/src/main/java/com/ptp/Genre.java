package com.ptp;

public class Genre 
{
    private int id;
    private String name;

    public Genre(int id, String name) 
    {
        this.id = id;
        this.name = name;
    }

    public int getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    @Override
    public String toString() 
    {
        return "Genre: id=" + id + ", name=" + name;
    }
}
