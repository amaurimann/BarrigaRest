package br.ce.wcaquino.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import br.ce.wcaquino.rest.core.Base_Test;


public class BarrigaTest extends Base_Test {
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get("/contas")// ja esta referenciado ao APP_BASE_URL
		.then()
			.statusCode(401) // 401 status Unauthorized
		
		;	
		
	}
	
	
	@Test
	public void deveIncluirContaComSucesso() {
		//login enviado via MAP
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "amaurimoraismann@gmail.com");
		login.put("senha", "123456");
		
		String token = given() // metódo vai retornar um token para String token
			.body(login)//dado o login email e senha
		.when()
			.post("/signin")
		.then()
			.statusCode(200)
			.extract().path("token")
			;
				
		given() 
			.header("Authorization", "JWT " + token)
			.body("{\"nome\": \"conta qualquer\"}\n")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			;
		
		
	}
}
