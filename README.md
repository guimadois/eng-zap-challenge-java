# Code Challenge Grupo Zap
Code challenge Grupo Zap

API responsável por filtrar anúncios de venda e locação de imóveis elegíveis para o grupo Zap e Viva Real. Ao inicializar a aplicação são carregados em memória todos os anúncios disponibilizados e serão filtrados de acordo com o endpoint chamado.

### Informações sobre o projeto

Desenvolvido em Java 11 e utilizando framework Spring Boot.
Utilizado arquivo application.properties para carga de parâmetros necessários nas regras de filtro solicitadas durante o desafio. 


## Execução

### Pré-requisito

Docker instalado localmente

### Baixando e executando a imagem

O comando abaixo fará o dowload e execução da imagem.

`
docker run -p 8080:8080 guimadois/eng-zap-challenge-java
`

### Exemplos de chamadas dos endpoints

 * Grupo Zap
`
http://localhost:8080/buscar/zap
`

 * Viva Real
`
http://localhost:8080/buscar/viva-real
`

### Paginação

Para utilizar a paginação é necessário adicionar os parâmetros 'page' e 'size' na URL, conforme exemplo:

`
http://localhost:8080/buscar/viva-real?page=2&size=1000
`
