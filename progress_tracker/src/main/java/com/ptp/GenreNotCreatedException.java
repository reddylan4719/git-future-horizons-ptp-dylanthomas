package com.ptp;

public class GenreNotCreatedException extends Exception
{
    private static final long serialVersionUID = 1L;

	public GenreNotCreatedException(Genre genre) 
    {
		super("Genre could not be created: " + genre.toString());
	}
}
