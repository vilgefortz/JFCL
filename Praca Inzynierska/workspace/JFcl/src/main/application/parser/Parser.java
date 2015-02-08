package main.application.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.fcl.application.Application;
import main.fcl.application.Context;
import main.fcl.application.FunctionBlock;
import main.fcl.file.Console;

public class Parser {
	public String document;
	public char[] doc;
	public int pointer=0;
	public main.application.Application app;
	public boolean done=false;
	public String word;
	public Parser () {
	
	}
	public Parser (String content) {
		this.document=content;
		this.doc=content.toCharArray();
	}
	public Parser (File file) {
		try {
			document = new Scanner(file).useDelimiter("\\Z").next();
		} catch (IOException e) {
			Logger.getGlobal()
					.log(Level.SEVERE, "Cannot find file " + file);
		}
		if (document!=null) doc = document.toCharArray();
		log ("Opened file "+file.getAbsolutePath() + ", content:\n"+document);
	}
	private static final ParserFunction IGNORE = new ParserFunction () {
		@Override
		public void commit(Parser p) {
		}
	};
	public void log (String msg) {
		Logger.getGlobal().log(Level.INFO,msg);
	}
	public ParserAction expectReg (String regex) {
		int temp = pointer;
		regex = "(?is)^"+ regex;
		moveOnTrailing();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(document.substring(pointer));
		String result = "";
		if (matcher.find()) result=matcher.group (); 
		this.word = result;
		ParserAction action = new ParserAction (this,pointer+result.length(),!result.equals(""));
		log ("Expect regex : " + regex + ", found : "+ (result.equals("")?"false":result) +", at point : " + pointer);
		if (result.equals("")) pointer = temp;
		return action;
	}
	public ParserAction expect (String S) {
		int temp = pointer;
		moveOnTrailing();
		this.word=S;
		boolean found = (S.equalsIgnoreCase(document.substring(pointer,pointer+S.length())));
		ParserAction action = new ParserAction (this,pointer+S.length(),found);
		log ("Expect : " + S + ", found : "+ found +", at point : " + pointer);
		if (!found) pointer = temp;
		return action;
	}
	
	private ParserAction expectRegForce(String reg) {
		ParserAction pc = this.expectReg(reg);
		int line = this.countLines(pointer);
		if (!pc.isFound()) Console.log("Expected " + reg,line,pointer);
		return pc;
	}
	
	private ParserAction expectForce(String S) {
		ParserAction pc = this.expect(S);
		int line = this.countLines(pointer);
		if (!pc.isFound()) Console.log("Expected " + S,line,pointer);
		return pc;
	}
	private int countLines(int ptr) {
		int count = 0;
		if (doc.length > ptr)
			for (int i = 0; i <= ptr; i++) {
				if (doc[i] == '\n')
					count++;
			}
		return count;	
	}
	private void moveOnTrailing() {
		while (Character.isWhitespace(doc[pointer++])) if (pointer==doc.length) return;
		pointer--;
	}
	public ParserAction expectWord() {
		return this.expectReg("[a-z][a-z0-9_]*");
	}
	public static final String COMMENT = "(?mis)\\(\\*.*?\\*\\)";
	public void parse() {
		Application app = new Application();
		expectReg(COMMENT).execute(IGNORE);
		expectForce("function_block").execute(p->{
			FunctionBlock fb = new FunctionBlock();
			app.functionBlocks.add(fb);
			expectReg(COMMENT).execute(IGNORE);
			expectWord().execute(p1->{
				fb.name=p1.word;
			});
			expectForce("var_input").execute(p1->{
				expectReg(COMMENT).execute(IGNORE);
				
			});
		});
		app.saveJSON();
		Console.printLog();
	}
}
