package main.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import main.application.enviroment.Enviroment;
import main.application.parser.Parser;

public class Application {
	private Enviroment enviroment = new Enviroment ();
	@Expose
	public FunctionBlocks functionBlocks =new FunctionBlocks(); 
	
	public static void main (String [] args) throws FileNotFoundException {
		Parser p = new Parser(new File ("test.fcl"));
		p.parse();
		System.out.println(p.app.toJson());
		
	}
	private String toJson() throws FileNotFoundException {
		
			Gson gson = new GsonBuilder().
					excludeFieldsWithoutExposeAnnotation().
					setPrettyPrinting().
					create();
		 return gson.toJson(this);
		
		
	}
	public Enviroment getEnv() {
		return enviroment;
	}
}