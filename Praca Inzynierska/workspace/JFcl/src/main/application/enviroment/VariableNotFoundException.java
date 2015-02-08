package main.application.enviroment;

public class VariableNotFoundException extends Exception {

	public VariableNotFoundException(String name) {
		super ("Variable with name " + name + " does not exists");
	}
	
}
