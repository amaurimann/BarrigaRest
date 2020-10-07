# BarrigaRest

<b>Cenários de testes da API</b>

Rota: http://barrigarest.wcaquino.me

1: Não deve acessar api sem token
<br>Get/contas rota = http://barrigarest.wcaquino.me/contas

2: Deve incluir uma conta com sucesso
<br>Post/signin rota = http://barrigarest.wcaquino.me/contas
<br>Enviando email e senha
<br>Post/contas enviando nome da conta 

3: Deve alterar conta com sucesso
<br>Put/contas/:id inserir o nome da nova conta inserida anteriormente

4: Não deve inserir conta com nome repetido
<br>Post/conta validar nome

5: Deve inserir movimentação com sucesso
Post/transacoes enviando dados<br>
<b>Conta_id
<br>descricao
<br>envolvido
<br>Tipo(DESP/REC)
<br>Data_transacao(dd/mm/yyyy)
<br>Data_pagamento(dd/mm/yyyy)
<br>Valor(0.00f)
<br>Status(true/false)<b>

6: Deve validar campos obrigatórios na movimentação
<br>Post/transacoes enviar campos e validar 

7: Nãa deve cadastrar transação futura
<br>Post/transacao

8: Não deve remover uma conta com movimentação
<br>Delete/contas/:id 

9: Deve calcular saldo das contas
<br>Get/saldo só volta contas q foram movimentadas

10: Deve remover uma movimentação
<br>Delete/transacoes/:id


