package main.application.rules;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.application.andmethods.AndMethod;
import main.application.functionblock.FunctionBlock;
import main.application.functionblock.Ruleblock;
import main.application.variable.term.Term;
import main.application.variables.BaseFunctionVariable;
import main.application.variables.InlineVariableNotFoundException;
import main.application.variables.InputVariableNotFoundException;

import com.google.gson.annotations.Expose;

public class DefaultActionFactory {
	protected String IS = "is";
	protected String OR = "or";
	protected String AND = "and";
	private Ruleblock ruleblock;
	public DefaultActionFactory (Ruleblock rb) {
		this.ruleblock=rb;
	}
	public Action createAction(String text, Rule r) throws RuleParsingException, InlineVariableNotFoundException, InputVariableNotFoundException {
		text = text.trim();
		text = this.dropBrackets (text);
		int pos = findFirstNotEnclosed ("or",text);
		if (pos<0) { //no or, search for and 
			pos = findFirstNotEnclosed ("and",text);
				if (pos<0) {
					return this.parseSingleExpression(text,r);
				}
			return this.createAndAction (text,pos,r);
		}
		return this.createOrAction (text,pos,r);
	}

	private Action createOrAction(String text, int pos, Rule ru) throws RuleParsingException, InlineVariableNotFoundException, InputVariableNotFoundException {
		String left = text.substring(0, pos);
		String right = text.substring(pos + OR.length());
		Action l = this.createAction(left,ru);
		Action r = this.createAction(right,ru);
		if (l==null || r ==null) throw new RuleParsingException("Unknown error at parsing rule expression");
		return new OrAction (l,r,this.getAndMethod());
	} 
	private Action createAndAction(String text, int pos, Rule ru) throws RuleParsingException, InlineVariableNotFoundException, InputVariableNotFoundException {
		String left = text.substring(0, pos);
		String right = text.substring(pos+AND.length());
		Action l = this.createAction(left,ru);
		Action r = this.createAction(right,ru);
		if (l==null || r ==null) throw new RuleParsingException("Unknown error at parsing rule expression");
		return new AndAction (l,r,this.getAndMethod());
	}
	protected AndMethod getAndMethod() {
		return this.ruleblock.getAndMethod();
	}

	private Action parseSingleExpression(String text, Rule r) throws RuleParsingException, InlineVariableNotFoundException, InputVariableNotFoundException {
		FunctionBlock fb = this.ruleblock.getFunctionBlock();
		String varName = this.getFirstWord (text);
		if (varName.equals("")) throw new RuleParsingException("Expected variable name");
		BaseFunctionVariable v = fb.getLeftVariable(varName);
		logger.info("Found left hand variable " + varName);
		r.addDepenedency (v);
		String is = this.getFirstWord(text=text.substring(varName.length()).trim());
		if (!is.equalsIgnoreCase(IS)) throw new RuleParsingException("Expected keyword 'is' after variable '" + varName + "'");
		String rightSide = text=text.substring(IS.length()).trim();
		Term t = this.parseTerm (rightSide,v,r);
		return new Action (){
			@Expose
			Term term = t;
			@Expose 
			BaseFunctionVariable var = v;
			@Override
			public double getValue() {
				return t.fun(var.getValue());
			}
		};	
	}
	private Term parseTerm(String text, BaseFunctionVariable v, Rule r) throws RuleParsingException {
		String word = getFirstWord(text = text.trim());
		if (v.hasTerm (word)) {
			String w =text.substring(word.length()).trim();
			if (!w.equals("")) throw new RuleParsingException("Unexpected '" + w + "' after term '" + word);
			return v.getTerm(word);
		}
		throw new RuleParsingException("Term '" + word + "' is not defined for variable '" + v.getName() + "'");
	}
	private static final Logger logger = Logger.getLogger("DefaultActionFactory");
	
	private String getFirstWord(String text,String regex) {
			regex = "(?is)^" + regex;
			text=text.trim();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(text);
			String result = "";
			if (matcher.find())
				result = matcher.group();
			return result;
		}
	private String getFirstWord (String text) {
		return this.getFirstWord(text, "[a-z][a-z0-9_]*");
	}
	
	private int findFirstNotEnclosed(String text,String main) throws RuleParsingException {
		Logger.getGlobal().info("Searching for '" + text + "' in '" + main+ "'.");
		char [] c = main.toCharArray();
		int count = 0;
		for (int i=0; i<c.length-text.length();i++) {
			if (c[i]=='(') count ++;
			if (c[i]==')') count --;
			if (count<0) throw new RuleParsingException("Unexpected ')', check your rule definition");
			if (count>0) continue;
			if ((new String (c,i,text.length())).equalsIgnoreCase(text)) {
				
				Logger.getGlobal().info("Found '" + text + "' in '" + main+ "' at position " + i);
				return i;
			};
			
		}
		if (count>0) throw new RuleParsingException("Unexpected '(', check your rule definition");
		return -1;
	}

	
	private String dropBrackets(String text) {
		if (text.startsWith("(") && text.endsWith(")")) {
			return text.substring(1, text.length()-1).trim();
		}
		return text;
	}

}
