package main.application.parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.application.Application;
import main.application.functionblock.FunctionBlock;
import main.application.variables.InputVariable;
import main.application.variables.OutputVariable;

public class Parser extends ParserBase {
	public Parser() {

	}

	public Parser(String content) {
		this.document = content;
		this.doc = content.toCharArray();
	}

	public Parser(File file) {
		try {
			document = new Scanner(file).useDelimiter("\\Z").next();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, "Cannot find file " + file);
		}
		if (document != null)
			doc = document.toCharArray();
		log("Opened file " + file.getAbsolutePath() + ", content:\n" + document);
	}

	public void parse() {
		this.eraseComments();
		app = new Application();
		expectForce("function_block").execute(
				p1 -> {
					FunctionBlock fb = new FunctionBlock(app);
					fb.setEnv(app.getEnv());
					app.functionBlocks.add(fb);
					expectWord("function block name").execute(p2 -> {
						fb.name = p2.word;
					});
					expectForce("var_input").execute(
							p2-> {
								while (!expect("end_var").isFound()) {
									InputVariable var = new InputVariable(fb);
									expectWordForce("variable name").execute(
											p3 -> {
												var.setName(p3.word);
												expectForce(":").execute(
														p4 -> {
															expectOneOfForce(
																	ApplcationConfig
																			.getVariableTypes(),
																	"variable type").execute(
																	p5 -> {
																		var.setType(p5.word);
																		expectForce(";").execute(
																				p6 -> {
																					fb.input.add(var);
																				});
																	});
														});
											});
								}
							});
					expectForce("var_output").execute(
							p2-> {
								while (!expect("end_var").isFound()) {
									OutputVariable var = new OutputVariable(fb);
									expectWordForce("variable name").execute(
											p3 -> {
												var.setName(p3.word);
												expectForce(":").execute(
														p4 -> {
															expectOneOfForce(
																	ApplcationConfig
																			.getVariableTypes(),
																	"variable type").execute(
																	p5 -> {
																		var.setType(p5.word);
																		expectForce(";").execute(
																				p6 -> {
																					fb.output.add(var);
																				});
																	});
														});
											});
								}
							});

				});
		// app.saveJSON();
		Console.printLog();
	}
}
