package main.application.parser;

import com.google.gson.annotations.Expose;

public class LogEntry {
	@Expose
	public int linepos;
	@Expose
	public int line;
	@Expose
	public int pos;
	@Expose
	public String entry;
}
