# OAuth2Retrofit2Android

OAuth2 é um protocolo / estrutura de autorização que permite que um aplicativo de terceiros obtenha acesso limitado a um serviço HTTP, seja em nome de um proprietário de recurso, organizando uma interação de aprovação entre o proprietário do recurso e o serviço HTTP ou permitindo que a ablação de terras tenha acesso em seu próprio nome.

A teoria parece complicada, mas a verdade é mais simples do que parece.

4 conceitos essenciais para OAuth2

/ ************************************************** *******************************

1. Funções Oauth2

************************************************** ****************************** /

1.- Proprietário do recurso - Proprietário do recurso
O proprietário do recurso é o usuário que autoriza um aplicativo a acessar sua conta.
O acesso do aplicativo à conta do usuário é limitado ao escopo da autorização concedida.
Exemplo, acesso somente leitura, acesso de gravação.
Exemplo de aplicativo mostrando dados bancários.
O dono do recurso, ou seja, nós, autorizamos o aplicativo que baixamos para nos mostrar os movimentos do banco, mas não damos permissão para fazer transferências.

2.- O Servidor de Recursos - Servidor de Recursos
O servidor que hospeda os recursos protegidos, capaz de aceitar e responder às solicitações de recursos protegidos usando tokens de acesso.
Por outras palavras, é o servidor que aloja as contas dos utilizadores protegidos e os dados, no exemplo seria o servidor do banco onde temos dinheiro e que nos permite ver os movimentos da conta, mas também fazer transferências e outros.

3.-O cliente
É um aplicativo que faz solicitações de recursos protegidos em nome do proprietário do recurso e com sua autorização.
Em outras palavras, o cliente é o aplicativo que deseja acessar a conta do usuário. Antes de fazer isso, ele deve ser autorizado pelo Proprietário do Recurso e a Autorização deve ser validada pela API do Servidor.
Exemplo: pode ser um aplicativo móvel, que solicita permissão para acessar a conta bancária de um cliente e ver suas transações bancárias.

4.- Servidor de Autorização - Servidor de Autorização
O servidor que emite tokens de acesso ao cliente após a autenticação com o proprietário do recurso e obtenção de autorização.

Do ponto de vista do Back-end, os desenvolvedores terão que criar o Servidor de Recursos e o Servidor de Autorização.

Do ponto de vista dos desenvolvedores de aplicativos Android ou iOS, os desenvolvedores terão que criar o Cliente.


/ ************************************************** *******************************

2. Tipos de concessão de autorização OAuth2

************************************************** ****************************** /

Uma concessão de autorização é uma credencial que representa a autorização do proprietário do recurso para acessar seus recursos protegidos usados ​​pelo cliente para obter um token de acesso.

1.- Código de Autorização

2.- Implica

3.- Credenciais de senha do proprietário do recurso

4.- Credenciais do cliente.

Nosso exemplo, usamos a concessão de credencial de senha do proprietário do recurso.

/ ************************************************** *******************************

3. Token OAuth2

************************************************** ****************************** /

Os tokens são cadeias de caracteres aleatórias específicas da implementação, são gerados pelo servidor de autorização e emitidos quando o cliente os solicita.
Existem duas classes de token em OAuth2

1.- Token de Acesso: É enviado com cada solicitação, para acessar um recurso, se o token for válido, nos devolve o recurso se não for. Tem uma vida útil muito curta, por exemplo, uma hora ou menos.

2.- Token de Refresco: serve para obter um novo token de acesso. Não é enviado com todas as solicitações ao servidor, só é enviado quando não temos o token de acesso ou ele expirou. Não é usado para obter recursos do servidor, mas apenas um novo token de acesso.

Sua duração é maior do que o token de acesso e depende da segurança que você deseja dar ao projeto.

/ ************************************************** *******************************

4.- O escopo do token de acesso

************************************************** ****************************** /

Em outras palavras, o usuário dá ao cliente acesso específico aos seus recursos. Exemplo que só pode ler ou também escrever.







