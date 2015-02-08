package main.application.variables;

import main.application.enviroment.Variable;

public class OutputVariable {
	private Variable main;
	
	public OutputVariable (Variable main) {
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
		return this.main.equals(((OutputVariable)obj).main);
	}
}
