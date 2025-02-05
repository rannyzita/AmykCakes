# README - Amyk Cakes

## 📌 Sobre o Projeto

Este projeto é um sistema para gerenciamento de uma confeitaria chamado **Amyk Cakes**. Ele foi desenvolvido utilizando **Java**, **JavaFX** para a interface gráfica, e **MySQL** para o banco de dados, utilizando **JDBC** para a conexão.

## 🔧 Configuração do Ambiente

Antes de rodar o projeto, certifique-se de ter o seguinte ambiente configurado:

- **Java Development Kit (JDK) 11 ou superior**
- **Eclipse IDE** (ou outra IDE compatível com Java)
- **JavaFX** corretamente configurado na IDE
- **MySQL Workbench** instalado
- **Driver JDBC para MySQL** configurado no projeto
- **Arquivo JAR do JavaFX e JDBC** referenciado corretamente no projeto (disponível no repositório)

## 📂 Configuração das Bibliotecas

1. **Referenciando o JavaFX**
   - No Eclipse, vá até **Project > Properties > Java Build Path**.
   - Clique na aba **Libraries** e em **Add External JARs**.
   - Selecione o arquivo **JavaFX JAR** disponível no repositório.
   - Vá até **Run Configurations**, selecione sua aplicação e adicione os seguintes argumentos em **VM arguments**:
     ```
     --module-path "caminho/para/javafx/lib" --add-modules javafx.controls,javafx.fxml
     ```

2. **Configurando o JDBC**
   - No Eclipse, vá até **Project > Properties > Java Build Path**.
   - Clique na aba **Libraries** e em **Add External JARs**.
   - Selecione o arquivo **JDBC JAR** disponível no repositório.
   - Confirme as alterações e salve o projeto.

## ⚙️ Configuração do Banco de Dados

1. **Modificar as credenciais de acesso ao banco de dados**

   - Abra o projeto no Eclipse.
   - Localize o arquivo responsável pela conexão com o banco (exemplo: `DatabaseConnection.java`).
   - Atualize os seguintes campos com suas credenciais do MySQL Workbench:
     ```java
     private static final String URL = "jdbc:mysql://<HOST>:<PORT>/<DATABASE_NAME>";
     private static final String USER = "<SEU_USUARIO>";
     private static final String PASSWORD = "<SUA_SENHA>";
     ```

2. **Criar o banco de dados e importar as tabelas**

   - No **MySQL Workbench**, crie uma nova database vazia:
     ```sql
     CREATE DATABASE amyk_cakes;
     ```
   - Baixe o arquivo do banco que está anexado no repositório.
   - Vá para **Server > Data Import**.
   - Escolha a opção **Import from Self-Contained File**.
   - Selecione o arquivo SQL  (tem mais de 1, ai tem que exportar um por vez), exportado que estará no repositório.
   - No campo **Default Target Schema**, selecione a database vazia criada.
   - Clique em **Start Import** e aguarde a conclusão.
   - Refaça esse passo, selecionando o Import from disk. 

## 🚀 Executando o Projeto

Após configurar o banco de dados, você pode rodar o sistema de duas formas:

1. **Usando a Interface Gráfica (JavaFX)**

   - Execute a classe principal da aplicação que inicializa o JavaFX.

2. **Usando o modo CLI (Linha de Comando)**

   - Há um arquivo no repositório com exemplos de entrada para testar o sistema pelo terminal.
   - Execute a classe `Main.java` para realizar testes diretamente.

## 🛠 Problemas e Soluções

Se ocorrer algum erro na conexão com o banco de dados, verifique:

- Se o MySQL Workbench está rodando corretamente.
- Se a URL, porta, usuário e senha no código estão corretos.
- Se a database foi importada corretamente.
- Se as bibliotecas do JavaFX e JDBC estão corretamente configuradas no projeto.

## 📜 Licença

Este projeto é de uso acadêmico e não possui uma licença formal. Caso deseje contribuir, fique à vontade!

---

Feito com 💙 para a disciplina de POO!

