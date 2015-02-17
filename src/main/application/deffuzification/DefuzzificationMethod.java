package main.application.deffuzification;

import com.google.gson.annotations.Expose;

public class DefuzzificationMethod {
	@Expose
	private String name;
	public DefuzzificationMethod(String method) {
		this.name=method;
	}
	public String getName() {
		return this.name;
	}
	@Override
	public boolean equals(Object obj) {
		return this.name.equalsIgnoreCase(((DefuzzificationMethod)obj).name);
	}
}
