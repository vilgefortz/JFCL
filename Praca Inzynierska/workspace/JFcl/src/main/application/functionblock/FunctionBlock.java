package main.application.functionblock;

import main.application.variables.InputVariables;
import main.application.variables.OutputVariables;

public class FunctionBlock {
	InputVariables input;
	OutputVariables output;
	Ruleblocks ruleblocks;
	public void execute () {
		ruleblocks.execute();
	}
}
