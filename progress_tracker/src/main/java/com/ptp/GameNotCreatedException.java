package com.ptp;

public class GameNotCreatedException extends Exception
{
    private static final long serialVersionUID = 1L;

	public GameNotCreatedException(Game game) 
    {
		super("Game could not be created: " + game.toString());
	}
}

