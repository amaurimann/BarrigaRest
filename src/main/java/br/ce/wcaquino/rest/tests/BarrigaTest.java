package br.ce.wcaquino.rest.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.rest.core.Base_Test;


public class BarrigaTest extends Base_Test {
	
	private String TOKEN;
	
	@Before
	public void login() {
		//login enviado via MAP
				Map<String, String> login = new HashMap<>();
				login.put("email", "amaurimoraismann@gmail.com");
				login.put("senha", "123456");
				
				TOKEN = given() // metódo vai retornar um token para String token
					.body(login)//dado o login email e senha
				.when()
					.post("/signin")
				.then()
					.statusCode(200)
					.extract().path("token")
					;
	}
	
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
				
		given() 
			.header("Authorization", "JWT " + TOKEN)// enviando token na requisicao
			.body("{\"nome\": \"conta qualquer\"}\n")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)// 201 status de inclusao com sucesso
			;
		
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		given()
		.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta alterada\"}")
		.when()
			.put("/contas/281292") // put = alterando 281292 é o id da conta
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", is("conta alterada"))
		;
	}
	
	@Test
	public void naoDeveInserirContaComMesmoNome() {
		given()
		.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta alterada\"}")
		.when()
			.post("/contas") // post = inserindo 281292 é o id da conta
		.then()
			.log().all()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"))
		;
	}
}


