package br.ce.wcaquino.rest.core;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class Base_Test implements Constantes {
	
	@BeforeClass
	public static void setup() {
		
		//System.out.println("Passou Aqui");
		RestAssured.baseURI = APP_BASE_URL; // referencia a classe constantes
		RestAssured.port = APP_PORT;// referencia a classe constantes
		RestAssured.basePath = APP_BASE_PATH;// referencia a classe constantes
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(APP_CONTENTE_TYPE);// referencia a classe constantes
		RestAssured.requestSpecification = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIME_OUT));// referencia a classe constantes
		RestAssured.responseSpecification = resBuilder.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		
	}
	
	

}
