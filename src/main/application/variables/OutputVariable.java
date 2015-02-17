package main.application.variables;

import main.application.accumulation.AccumulationMethod;
import main.application.accumulation.AccumulationMethodNotRecognisedException;
import main.application.deffuzification.DeffuzzificationMethodNotRecognisedException;
import main.application.deffuzification.DefuzzificationMethod;
import main.application.deffuzification.DefuzzificationMethodNotRecognisedException;
import main.application.functionblock.FunctionBlock;

import com.google.gson.annotations.Expose;

public class OutputVariable extends BaseFunctionVariable {
	@Expose
	private AccumulationMethod accuMethod;
	@Expose
	private DefuzzificationMethod deffMethod;

	public OutputVariable(FunctionBlock fb) {
		this.fb = fb;
	}

	public OutputVariable(String name) {
		this.name = name;
	}

	public OutputVariable(String name, FunctionBlock fb) {
		this.name = name;
		this.fb = fb;
		this.var = this.fb.getEnv().getVariable(name);
	}

	public void setAccuMethod(String method)
			throws AccumulationMethodNotRecognisedException {
		this.accuMethod = this.fb.getApp().getAccuMethod(method);
	}

	public void setDefuzzificationMethod(String method) throws DefuzzificationMethodNotRecognisedException {
		this.deffMethod = this.fb.getApp().getDefuzzificationMethod(method);
		
	}
}
