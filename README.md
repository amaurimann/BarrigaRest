# BarrigaRest

Cenários de testes da API

Rota: http://barrigarest.wcaquino.me

1: Não deve acessar api sem token
Get/contas rota = http://barrigarest.wcaquino.me/contas

2: Deve incluir uma conta com sucesso
Post/signin rota = http://barrigarest.wcaquino.me/contas
Enviando email e senha
Post/contas enviando nome da conta 

3: Deve alterar conta com sucesso
Put/contas/:id inserir o nome da nova conta inserida anteriormente

4: Não deve inserir conta com nome repetido
Post/conta validar nome

5: Deve inserir movimentação com sucesso
Post/transacoes enviando dados
Conta_id
descricao
envolvido
Tipo(DESP/REC)
Data_transacao(dd/mm/yyyy)
Data_pagamento(dd/mm/yyyy)
Valor(0.00f)
Status(true/false)

6: Deve validar campos obrigatórios na movimentação
Post/transacoes enviar campos e validar 

7: Nãa deve cadastrar transação futura
Post/transacao

8: Não deve remover uma conta com movimentação
Delete/contas/:id 

9: Deve calcular saldo das contas
Get/saldo só volta contas q foram movimentadas

10: Deve remover uma movimentação
Delete/transacoes/:id


