package com.ptp;

public class UserNotCreatedException extends Exception
{
    private static final long serialVersionUID = 1L;

	public UserNotCreatedException(User user) 
    {
		super("Account with the following username could not be created: " + user.getUsername());
	}
}
