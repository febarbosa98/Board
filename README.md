# Board  de tarefas

Projeto desenvolvido como parte do desafio **"Criando seu Board de Tarefas com Java"** promovido pela [DIO Academy](https://www.dio.me).

O objetivo é criar um sistema simples de gerenciamento de tarefas utilizando Java puro, boas práticas de arquitetura e persistência com JDBC

## Funcionalidades

- Criar Boards: Crie boards personalizados com colunas iniciais, pendentes, final, e de cancelamento.
- Gerenciar Colunas: Adicione colunas extras além das padrões.
- Gerenciar Cards: Crie, mova, bloqueie, desbloqueie, cancele e visualize cards.
- Visualização: Veja detalhes do board, colunas e cards, incluindo status de bloqueio e histórico.
- Persistência: Todos os dados são salvos em banco de dados relacional via JDBC.
- Migrações: Estrutura do banco gerenciada com Liquibase.
- Estrutura do Projeto

## Tecnologias Utilizadas

- Java
- JDBC
- Liquibase
- Gradle
- Banco de Dados Relacional (ex: MySQL)
- Outras dependências relevantes

## Como Executar

1. Clone o repositório
2. Configure o banco de dados (MySQL ou outro compatível).
3. Ajuste as configurações de conexão em ConnectionConfig.
4. Execute as migrações
5. Compile e execute o projeto

```sh
# Exemplo de comandos
./gradlew build
java -cp build/classes/java/main br.com.dio.Main
```

## Estrutura do Projeto

```
src/
  main/
    java/
      br/com/dio/
        Main.java
        ui/
        service/
        persistence/
        dto/
        exception/
```


## Requisitos

- Java 17+
- Gradle
- Banco de dados relacional (MySQL recomendado)

## Autor

Desenvolvido por **Fernando Barbosa** como parte do bootcamp **[DIO GFT Start #7 - Java](https://www.dio.me/bootcamps)**.
