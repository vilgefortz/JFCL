package main.application.variable.term.types;

import com.google.gson.annotations.Expose;

import main.application.variable.term.Term;
import main.application.variable.term.TermDeclarationErrorException;

public class Singleton extends Term {

	@Expose
	private String jsfunction;
	@Expose
	private double val;

	public Singleton(String termName, String definition) throws TermDeclarationErrorException {
		this.name = termName;
		this.type = "singleton";
		try {
		this.val = Double.parseDouble(definition);
		}
		catch (NumberFormatException e) {
			throw new TermDeclarationErrorException("Expected numeric value for singleton, found '" + definition +"'");
					
		}
		this.jsfunction = this.createJsFunction ();
	}

	private String createJsFunction() {
		return "function (val) { return val===this.val? 1:0}";
	}

	@Override
	public double fun(double val) {
		return val==this.val? 1: 0;
	}

}
