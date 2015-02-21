package main.application.variables;

import main.application.functionblock.FunctionBlock;

public class InlineVariable extends OutputVariable {
	public InlineVariable(FunctionBlock fb) {
		super(fb);
	}

	public InlineVariable(String name) {
		super (name);
	}

	public InlineVariable(String name, FunctionBlock fb) {
		super (name,fb);
	}
}
