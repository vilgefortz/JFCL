package test.application.enviroment;

import static org.junit.Assert.*;
import main.application.enviroment.Variable;
import main.application.enviroment.VariableNotFoundException;

import org.junit.Test;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class Enviroment {

	@Test
	public void testAddGetVariable() throws VariableNotFoundException {
		main.application.enviroment.Enviroment env = new main.application.enviroment.Enviroment();
		Variable var = new Variable("var", 4);
		Variable var2 = new Variable("var2", 3);
		env.add(var);
		env.add(var2);
		if (env.getVariable("var").getValue() != 4) {
			fail ("Retrieving variable from enviroment failed");
		}
		if (env.getValueOf("var2") != 3) {
			fail ("Retrieving variable value from enviroment failed");
		}
		try {
			env.getValueOf("var4");
			fail ("Raising exception failure");
		}
		catch (VariableNotFoundException e) {
			
		}
	}

}
