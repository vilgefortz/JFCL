package main.application.variable.term;

import main.application.Application;

//used with chain responsibility
public abstract class TermGenerator {
	private TermGenerator tg;
	private Application app;
	public TermGenerator getNext () throws TermDefinitionNotRecognisedException {
		return tg;
	};
	public Term generateTerm (String termName, String definition) throws TermDefinitionNotRecognisedException, TermDeclarationErrorException {
		if (this.isResponsible(definition)) {
			return this.generate(termName, definition);
		}
		else {
			if (this.getNext()==null) {
				throw new TermDefinitionNotRecognisedException (definition);
			}
			return getNext().generateTerm(termName, definition);
		}
	}
	protected abstract Term generate(String termName, String definition) throws TermDeclarationErrorException;
	protected abstract boolean isResponsible(String definition) throws TermDeclarationErrorException;
	public TermGenerator addTermGenerator(TermGenerator tg, Application app) {
		tg.setApplication(app);
		if (this.tg==null) {
			this.tg=tg;
		}
		else {
			this.tg.addTermGenerator(tg, app);
		}
		return this;
	}
	public void setApplication(Application app) {
		this.app=app;
	}	
	
}