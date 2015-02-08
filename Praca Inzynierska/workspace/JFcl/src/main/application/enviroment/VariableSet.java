package main.application.enviroment;

import java.util.ArrayList;

public class VariableSet extends ArrayList<Variable>{
	
	public Variable getVariable (String name) throws VariableNotFoundException {
		int index = -1;
		if ((index = this.indexOf(new Variable(name))) >= 0 ) {
			return this.get(index);
		}
		throw new VariableNotFoundException (name);
	}
	
	public double getValueOf (String name) throws VariableNotFoundException {
		int index = -1;
		if ((index = this.indexOf(new Variable(name))) >= 0 ) {
			return this.get(index).getValue();
		}
		throw new VariableNotFoundException (name);
	}	
}
