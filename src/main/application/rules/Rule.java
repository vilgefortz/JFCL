package main.application.rules;

import java.util.ArrayList;
import java.util.List;

import main.application.variables.BaseFunctionVariable;

import com.google.gson.annotations.Expose;

public class Rule {
	@Expose
	private String text;
	@Expose
	private String name;
	@Expose
	Cause cause;
	@Expose
	Effect effect;
	private List<BaseFunctionVariable> dependend = new ArrayList<BaseFunctionVariable> ();
	private List<BaseFunctionVariable> affecting = new ArrayList<BaseFunctionVariable> ();
	public void setText(String text) {
		this.text = text;
	}
	
	public void setName(String name) {
		this.name=name;
	}

	public Rule (String name) {
		this.name=name;
	}
	public Rule (String name, String text) {
		this(name);
		this.text=text;
	}

	public void addDepenedency(BaseFunctionVariable v) {
		if (this.dependend.contains(new BaseFunctionVariable(v.getName())))
		this.dependend.add(v);
	}
}
