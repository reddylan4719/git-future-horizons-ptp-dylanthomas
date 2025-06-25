package com.ptp;

public class TaskNotCreatedException extends Exception
{
    private static final long serialVersionUID = 1L;

	public TaskNotCreatedException(Task task) 
    {
		super("The following task could not be created: " + task.toString());
	}
}
