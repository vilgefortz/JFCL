package main.application.variable.term;

import main.application.Application;
import main.application.variable.term.types.Singleton;

public class DefaultTermFactory extends TermFactory {
	public static final TermGenerator POINTS = new TermGenerator () {

		@Override
		protected Term generate(String termName, String definition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected boolean isResponsible(String definition) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	public static final TermGenerator SINGLETON = new TermGenerator () {
		
		@Override
		protected Term generate(String termName, String definition) throws TermDeclarationErrorException {
				return new Singleton (termName, definition); 
		}

		@Override
		protected boolean isResponsible(String definition) throws TermDeclarationErrorException {
			if (definition.trim().length()==0) throw new TermDeclarationErrorException("Expected term definition");
			char first = definition.trim().toCharArray()[0]; 
			return Character.isDigit(first) || first=='-' || first == '.';
		}
	};

	public DefaultTermFactory () {
		
	}

	public DefaultTermFactory(Application application) {
		this.app = application;
		this.addTermGenerator(SINGLETON);
	}
}
