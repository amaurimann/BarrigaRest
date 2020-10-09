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
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		
		given()
		.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post("/transacoes") // post = inserindo 
		.then()
			.log().all()
			.statusCode(201) //status code para inserção..
		;
	}
	
	@Test
	public void deveInserirMovimentacaoComSucessoTest2() {

		Movimentacao mov2 = new Movimentacao();

		mov2.setData_transacao("02/02/2000");
		mov2.setData_pagamento("12/05/2010");
		mov2.setDescricao("Desc da Mov2");
		mov2.setEnvolvido("Envolv na Mov2");
		mov2.setValor(300f);
		mov2.setConta_id(281292);
		mov2.setTipo("REC");
		mov2.setStatus(true);


		given()
			.header("Authorization", "JWT " + TOKEN)//passando credenciais
			.body(mov2)//pasando valores por objeto
		.when()
			.post("/transacoes")//caminho de destino
		.then()
			.log().all()//log all no console
			.statusCode(201)//status 201 de insercao com sucesso
		;		

	}

	@Test
	public void deveValidarCamposObrigatoriosNaMovimentacao() {

		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{}")
		.when()
			.post("/transacoes")
		.then()
		.log().all()
			.statusCode(400) // status 400 bad request error
			.body("$", hasSize(8)) // $ nó raiz, hasSize 8 é a qtd de mensagens que vao aparecer informando os campos obrigatórios que precisam ser preenchidos
			.body("msg", hasItems(
					"Data da Movimentação é obrigatório",
					"Data do pagamento é obrigatório",
					"Interessado é obrigatório",
					"Valor é obrigatório",
					"Valor deve ser um número",
					"Conta é obrigatório",
					"Situação é obrigatório"
					))
			;
	}
	
	@Test
	public void naoDeveInserirMovimentacaoComDataFutura() {
	
	Movimentacao mov = getMovimentacaoValida();
	mov.setData_transacao("12/10/2020");
	
	given()
	.header("Authorization", "JWT " + TOKEN)
		.body(mov)
	.when()
		.post("/transacoes") // post = inserindo 
	.then()
		.log().all()
		.statusCode(400) //status code para inserção..
		.body("$", hasSize(1))
		.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
	}
	
	@Test
	public void naoDeveRemoverContaComMovimentacao() {
		
	given()
		.header("Authorization", "JWT " + TOKEN)
	.when()
		.delete("/contas/281292") 
	.then()
		.statusCode(500) //status code	
		.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	
	@Test
		public void deveCalcularSaldoContas() {
		
		given()
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.get("/saldo")
		.then()
			.log().all()
			.statusCode(200)
			.body("find{it.conta_id == 281292}.saldo", is("800.00"))
		;
	}
	
	@Test
	public void deveRemoverMovimentacao() {
	
	given()
		.header("Authorization", "JWT " + TOKEN)
	.when()
		.delete("/transacoes/254586") //254586 id da movimentacao a ser excluida, mod dev do browser inspetor
	.then()
		.log().all()
		.statusCode(204)
	;
}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(281292);
//		mov.setUsuario_id(usuario_id);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao("01/01/2000");
		mov.setData_pagamento("10/05/2010");
		mov.setValor(100f);
		mov.setStatus(true);
		
		return mov;
		
		
	}
	
}


