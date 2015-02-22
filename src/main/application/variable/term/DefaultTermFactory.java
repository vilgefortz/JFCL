package main.application.variable.term;

import java.util.ArrayList;
import java.util.List;

import main.application.Application;
import main.application.parser.utils.ParsingUtils;
import main.application.variable.term.types.SingletonTerm;

public class DefaultTermFactory extends TermFactory {
	public static final TermGenerator POINTS = new TermGenerator () {
		
		@Override
		protected Term generate(String termName, String definition) throws TermDeclarationErrorException {
			List<Point> p = this.generatePoints (definition);
			return new PointsTerm (termName, p);
		}

		private List<Point> generatePoints(String definition) throws TermDeclarationErrorException {
			List<Point> points = new ArrayList<Point> ();
			boolean finish = false;
			while (!definition.trim().equals("")) {
				definition = definition.trim();
				if (definition.equals("")) finish = true;
				if (!definition.startsWith("(")) throw new TermDeclarationErrorException("Point definition should start with '('");
				definition = definition.substring(1);
				int closingPos = definition.indexOf(")");
				if (closingPos<0) throw new TermDeclarationErrorException("Point definition is missing closing bracket");
				String pointDef = definition.substring(0, closingPos);
				Point p = generatePoint (pointDef);
				definition=definition.substring(pointDef.length()+1);
			}
			return points;
		}

		private Point generatePoint(String pointDef) {
			Point p = new Point ();
			String val = ParsingUtils.getFirstWord(pointDef=pointDef.trim(),"-?[0-9.]*");
			Double x = Double.parseDouble(val);
			String comma = ParsingUtils.getFirstWord(pointDef=pointDef.trim(),",");
			val = ParsingUtils.getFirstWord(pointDef=pointDef.trim(),"-?[0-9.]*");
			Double y = Double.parseDouble(val);
			return p;
		}

		@Override
		protected boolean isResponsible(String definition) {
			if (definition.trim().startsWith("(")) return true;
			return false;
		}
	};
	public static final TermGenerator SINGLETON = new TermGenerator () {
		
		@Override
		protected Term generate(String termName, String definition) throws TermDeclarationErrorException {
				return new SingletonTerm (termName, definition); 
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
		this.addTermGenerator(POINTS);
	}
}
