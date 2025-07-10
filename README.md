# Sistema de Gerenciamento de Pesquisas Acadêmicas - Versão para Neo4J

Este projeto é uma aplicação Java de console para gerenciamento de pesquisas acadêmicas. 
Utiliza um **driver** para se conectar a um banco de dados **Neo4J**.

---

## 📦 Funcionalidades

- Cadastrar, listar, alterar e remover:
  - Pesquisadores
  - Instituições
  - Orientandos
  - Projetos
  - Publicações
  - Financiamentos

- Consultas úteis:
  - Listar publicações com projeto e pesquisador
  - Listar projetos com instituições e financiamento
  - Listar projetos com coordenador e instituição
  - Listar projetos e pesquisadores participantes

---

## ⚙️ Tecnologias usadas

- Java 17+
- Neo4J
- IntelliJ (ou outro IDE compatível)

---

## 🛠️ Como instalar e executar

#### 1. Clone o repositório para um repositório seu local.
#### 2. Crie uma database no Neo4J com o nome desejado, definindo uma senha.
#### 3. Abra a pasta database do projeto.
#### 4. Abra especificamente o arquivo dump_neo4j.txt com um editor de texto e copie o script. (CTRL + A -> CTRL + C)
#### 5. Abra a Query Tool dentro da database criada no Neo4J, cole o script e execute selecionando tudo.
#### 6. Abra o projeto na IDE desejada. (recomendado IntelliJ)
#### 7. Modifique dentro do arquivo Conexao.java as variáveis user e senha para o usuário e senha do seu banco do Neo4J.
#### 8. Ainda no Conexao.java, modifique o uri para o uri da sua database. Exemplo: "neo4j://999.0.0.0:7777"
#### 9. Como o projeto foi gerado em Maven, as dependências são todas gerenciadas dentro do arquivo pom.xml, por isso
não é necessário configurar nenhum driver.
#### 10. Salve e execute a classe Principal.java para começar a utilizar.
