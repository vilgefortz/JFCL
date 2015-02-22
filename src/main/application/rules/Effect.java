package main.application.rules;

import main.application.variable.term.Term;
import main.application.variables.BaseFunctionVariable;
import main.application.variables.OutputVariable;

public abstract class Effect {
	public OutputVariable var;

	public Effect(BaseFunctionVariable v) throws RuleParsingException {
		try {
			this.var = (OutputVariable) v;

		} catch (Exception e) {
			throw new RuleParsingException("Variable '" + v
					+ "' cannot be defuzzified");
		}
	}

	public abstract Term getTerm(double level);

	public void execute(double level) {
		this.var.accumulateTerm(this.getTerm(level));
	}
}
