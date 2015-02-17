package main.application.variables;

import java.util.ArrayList;
import java.util.List;

import main.application.enviroment.Variable;
import main.application.functionblock.FunctionBlock;
import main.application.variable.term.Term;

import com.google.gson.annotations.Expose;

public class BaseFunctionVariable {

	protected FunctionBlock fb;
	protected String name;
	@Expose
	private String type;
	@Expose
	protected Variable var;
	@Expose
	private List<Term> terms = new ArrayList<Term> ();

	public BaseFunctionVariable() {
		super();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (name!=null && fb!= null ) this.var = this.fb.getEnv().getVariable(name);
		this.name = name;
	}

	public double getValue() {
		return fb.getEnv().getValueOf(name);
	}

	public void setValue(double value) {
		fb.getEnv().getVariable(name).setValue(value);
	}

	@Override
	public boolean equals(Object obj) {
		return this.name.equalsIgnoreCase(((BaseFunctionVariable) obj).name);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void addTerm(Term term) {	
		this.terms.add(term);
	}

	public void setDefault(double val) {
		this.setValue(val);
	}

}