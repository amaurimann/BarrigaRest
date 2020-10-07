package br.ce.wcaquino.rest.tests;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.ce.wcaquino.rest.core.Base_Test;


public class BarrigaTest extends Base_Test {
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get("/contas")// ja esta referenciado ao APP_BASE_URL
		.then()
			.statusCode(401)
		
		;
		
		
		
		
		
	}

}
