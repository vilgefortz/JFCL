package main.application.variable.term;

import main.application.Application;

public class TermFactory {

	protected TermGenerator termGenerator;
	protected Application app;
	
	public TermGenerator addTermGenerator (TermGenerator tg) {
		if (this.termGenerator==null) { 
			this.termGenerator = tg;
			tg.setApplication(app);
		}
		else this.termGenerator.addTermGenerator(tg, app);
		return termGenerator;
	}
	public Term generateTerm(String termName, String word) throws TermDefinitionNotRecognisedException, TermDeclarationErrorException, TermDefinitionHasKeywordException {
		//remove last semicolon
		
		word=word.trim().substring(0, word.length()-1);
		String w = null;
		if (( w = app.getParser().hasKeyword(word))!=null) throw new TermDefinitionHasKeywordException (w);
		return termGenerator.generateTerm(termName, word);
	}

}
