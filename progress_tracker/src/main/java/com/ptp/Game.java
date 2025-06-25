package com.ptp;

import java.sql.Date;

public class Game 
{
    private int id;
    private String name;
    private int genreID;
    private String madeBy;
    private Date releasDate;
    
    public Game(int id, String name, int genreId, String madeBy, Date releasDate) 
    {
        this.id = id;
        this.name = name;
        this.genreID = genreId;
        this.madeBy = madeBy;
        this.releasDate = releasDate;
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

    public int getGenreID() 
    {
        return genreID;
    }

    public void setGenreID(int genreId) 
    {
        this.genreID = genreId;
    }

    public String getMadeBy() 
    {
        return madeBy;
    }

    public void setMadeBy(String madeBy) 
    {
        this.madeBy = madeBy;
    }

    public Date getReleasDate() 
    {
        return releasDate;
    }

    public void setReleasDate(Date releasDate) 
    {
        this.releasDate = releasDate;
    }

    @Override
    public String toString() 
    {
        return "Games: id=" + id + ", name=" + name + ", genreId=" + genreID + ", madeBy=" + madeBy + ", releasDate="
                + releasDate;
    }
}
