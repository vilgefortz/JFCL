package main.application.variables;

import java.util.ArrayList;

import main.application.enviroment.Variable;
import main.application.enviroment.VariableNotFoundException;

public class InputVariables extends ArrayList<InputVariable> {

	public InputVariable getInputVariable (String name) throws InputVariableNotFoundException {
		int index = -1;
		if ((index = this.indexOf(new InputVariable(new Variable(name)))) >= 0 ) {
			return this.get(index);
		}
		throw new InputVariableNotFoundException (name);
	}
	
	public double getValueOf (String name) throws InputVariableNotFoundException {
		int index = -1;
		if ((index = this.indexOf(new InputVariable(new Variable(name)))) >= 0 ) {
			return this.get(index).getValue();
		}
		throw new InputVariableNotFoundException (name);
	}
}
