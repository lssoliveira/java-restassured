# java-restassured

## Pré Requisitos
Para execução dos testes do projeto são necessárias as seguintes intalações:
* [Java JDK](https://www.oracle.com/br/java/technologies/downloads/),
* [Gradle](https://gradle.org/install/),
* [IntelliJ](https://www.jetbrains.com/idea/download/?section=windows) (Opcional)

- _**Lembre-se de adicionar a variável de Ambiente do Java**_:
    - `JAVA_HOME` - Local da instalação do Java JDK

## Validando o ambiente
Após clonado o repositório, para preparar o ambiente, acesse a pasta raiz e execute o comando abaixo:
```sh
gradlew build
```
Para sistema MacOS ou Linux adicione `./` antes de todos os comandos:
```sh
./gradlew build
```

## Bibiliotecas Utilizadas
As dependências serão instaladas automaticamente no momento do build, de acordo com o listado no arquivo `build.gradle`.</p>
Algumas das bibliotecas mais importanes utilizadas no projeto são:
- [JUnit](https://junit.org/junit5/docs/current/user-guide/),
- [RestAssured](https://rest-assured.io/)

## Como Executar
### Execução Local
#### Todos os testes:
```sh
gradlew test
```

#### Testes parametrizados:
- Informar o caminho até o teste a ser executado:

Exemplo de Testes de Usuários:
```sh
gradlew test --tests "dummyJson.tests.UserTest"
```

Exemplo de Testes de Produtos:
```sh
gradlew test --tests "dummyJson.tests.UserTest.getTest"
```

### Execução Workflow Github Actions
- Execução de todos os testes, sem informar parametros, 
- Execução de teste específico, informando parametro de teste.

## Estrutura
- **Abrangência**: Esse projeto abrange testes de API (BackEnd).
- **Estrutura**:
  - `/domain`: Responsável por realizar as ações nas api e rodas, é onde estão todos os métodos de requests.

  - `/set`: Responsável por fazer o mapeamento dos campos a serem utilizados nas requisições.

  - `/schema`: Arquivos onde estão os schemas para a validação dos contratos retornados nas requests.

  - `/tests`: Responsável por realizar os testes, irá chamar todos os métodos e fazer as validações. 

## Relatório de testes:
### Local:
- `.html`: Gerado automaticamente no diretório: `./build/reports/**/*.html`. </p> Para acessar basta abrir o arquivo no navegador.
- `.xml`: Gerado automaticamente no diretório: `./build/test-results/**/*.xml`.

### GitHub:
- Será gerado o realtório `.html` automaticamente, ficando disponível para download no output da execução.

## Comportamentos Identificados da API
- Por se tratar de uma API publica para testes, ela não possui muitas validações, e aceita praticamente tudo que se é passado.
- Alguns cenários de exceção foram implementados, como dados invalidos.
- Um dos problemas encontrados foi que no endpoint: 
  - `get/users` o _id=12_ Não está voltando o campo _"city"_ -> _"company"."address"."city"_.
