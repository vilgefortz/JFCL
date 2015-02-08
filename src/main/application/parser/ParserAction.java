package main.application.parser;

public class ParserAction {
	private ParserBase parser;
	private boolean found;
	public boolean isFound() {
		return found;
	}
	public ParserAction (ParserBase parser, int pos, boolean found) {
		this.found = found;
		this.parser = parser;
		if (found) this.parser.pointer=pos;
	}
	public void execute (ParserFunction func) {
		if (found) func.commit(this.parser);
	}
}