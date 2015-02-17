package main.application.variable.term;

import com.google.gson.annotations.Expose;

public abstract class Term {
	@Expose
	protected String name="unknown";
	@Expose
	protected String type="unknown";
	@Expose
	protected double min;
	@Expose
	protected double max;
	public Term () {
		
	}
	public Term (String name) {
		this.name=name;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Term t=(Term)arg0;
		return t.name.equalsIgnoreCase(this.name);
	}
	public abstract double fun (double val);
	@Override
	public String toString() {
		return "TERM : "+name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}
}