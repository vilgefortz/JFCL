package main.application.accumulation;

import com.google.gson.annotations.Expose;

public class AccumulationMethod {
	@Expose
	private String name;
	
	public AccumulationMethod(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		return this.name.equalsIgnoreCase(((AccumulationMethod)obj).name);
	}
}
