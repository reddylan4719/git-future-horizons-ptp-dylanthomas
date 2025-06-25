package com.ptp;

public class Task 
{
    private int id;
    private int userID;
    private int gameID;
    private String progress;

    public Task(int id, int userID, int gameID, String progress) 
    {
        this.id = id;
        this.userID = userID;
        this.gameID = gameID;
        this.progress = progress;
    }

    public int getId() 
    {
        return id;
    }

    public int getUserID() 
    {
        return userID;
    }

    public void setUserID(int userID) 
    {
        this.userID = userID;
    }

    public int getGameID() 
    {
        return gameID;
    }

    public void setGameID(int gameID) 
    {
        this.gameID = gameID;
    }

    public String getProgress() 
    {
        return progress;
    }

    public void setProgress(String progress) 
    {
        this.progress = progress;
    }

    @Override
    public String toString() 
    {
        return "Task: id=" + id + ", userID=" + userID + ", gameID=" + gameID + ", progress=" + progress;
    }
}
