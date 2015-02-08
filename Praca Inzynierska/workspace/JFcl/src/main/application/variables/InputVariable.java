package main.application.variables;

import main.application.enviroment.Variable;

public class InputVariable {
	private Variable main;
	
	public InputVariable (Variable main) {
		this.main = main;
	}
	public String getName() {
		return main.getName();
	}
	public void setName(String name) {
		main.setName(name);
	}
	public double getValue() {
		return main.getValue();
	}
	public void setValue(double value) {
		main.setValue(value);
	}
	@Override
	public boolean equals(Object obj) {
		return this.main.equals(((InputVariable)obj).main);
	}
}
