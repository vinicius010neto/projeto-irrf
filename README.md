# Sistema IRRF com JSF

Aplicação Java EE monolítica com JSF e PrimeFaces, que permite o cadastro de funcionários, cálculo de IRRF por função SQL no PostgreSQL e geração de relatórios em PDF via JasperReports.

---

## Tecnologias Utilizadas

| Camada         | Tecnologia                 |
| -------------- | -------------------------- |
| View (UI)      | JSF 2.2 + PrimeFaces 6.2   |
| Backend        | Java EE 8 / Servlet + JDBC |
| Relatórios     | JasperReports 6.20.0       |
| Banco de Dados | PostgreSQL 17 (via Docker) |
| Servidor       | Apache Tomcat 9.0.104      |
| Gerenciador    | Maven (WAR)                |

---

## Estrutura de Diretórios

```shell
src/
├── main/
│   ├── java/
│   │   └── zdoc.irrf/
│   │       ├── bean/           # ManagedBeans (FuncionarioBean)
│   │       ├── dao/            # DAO com JDBC (FuncionarioDAO)
│   │       ├── model/          # Classe Funcionario
│   │       ├── report/         # Jasper (.jrxml, .jasper e GeradorRelatorio.java)
│   │       └── servlet/        # RelatorioFuncionarioServlet
│   ├── resources/              # (se necessário para relatórios externos)
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml
│       │   └── faces-config.xml
│       ├── funcionarios.xhtml  # Tela de cadastro e listagem
│       └── index.html
```

---

## Banco de Dados

### Tabela `funcionarios`

```sql
CREATE TABLE funcionarios (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  cpf VARCHAR(14) UNIQUE NOT NULL,
  salario NUMERIC(10,2) NOT NULL,
  proventos NUMERIC(10,2) DEFAULT 0,
  descontos NUMERIC(10,2) DEFAULT 0
);
```

### Função SQL `calcula_irrf`

```sql
CREATE OR REPLACE FUNCTION calcula_irrf(salario_base NUMERIC, outros_proventos NUMERIC)
RETURNS NUMERIC AS $$
DECLARE
  salario_total NUMERIC := salario_base + outros_proventos;
  imposto NUMERIC := 0;
BEGIN
  IF salario_total <= 2112.00 THEN
    imposto := 0;
  ELSIF salario_total <= 2826.65 THEN
    imposto := salario_total * 0.075 - 158.40;
  ELSIF salario_total <= 3751.05 THEN
    imposto := salario_total * 0.15 - 370.40;
  ELSIF salario_total <= 4664.68 THEN
    imposto := salario_total * 0.225 - 651.73;
  ELSE
    imposto := salario_total * 0.275 - 884.96;
  END IF;

  RETURN ROUND(imposto, 2);
END;
$$ LANGUAGE plpgsql;
```

---

## Funcionalidades

- [x] Cadastro de funcionários
- [x] Listagem com tabela dinâmica
- [x] Edição e exclusão de registros
- [x] Cálculo de IRRF via função SQL (PL/pgSQL)
- [x] Relatório PDF gerado via JasperReports

---

## 📜 Relatórios

- Arquivo: `funcionario.jrxml` compilado para `funcionario.jasper`
- Gerado com `JRBeanCollectionDataSource` baseado em `Funcionario`
- Exportado por `RelatorioFuncionarioServlet.java`

---

## 🐳 Docker (PostgreSQL)

```yaml
version: "3.1"
services:
  postgres:
    image: postgres:17
    container_name: postgres_irrf
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: irrfdb
    ports:
      - "5432:5432"
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
```

---

## ▶️ Como Executar

1. **Suba o PostgreSQL via Docker**

   ```bash
   docker-compose up -d
   ```

2. **Importe o projeto no Eclipse (como Maven Project)**

3. **Compile o `.jrxml` em `.jasper` via iReport ou Jaspersoft Studio**

4. **Rode no Tomcat e acesse:**
   ```
   http://localhost:8080/projeto-irrf/funcionarios.xhtml
   ```

---

## 📂 Dependências Principais (`pom.xml`)

```xml
<dependency>
  <groupId>javax.faces</groupId>
  <artifactId>javax.faces-api</artifactId>
  <version>2.2</version>
</dependency>
<dependency>
  <groupId>org.primefaces</groupId>
  <artifactId>primefaces</artifactId>
  <version>6.2</version>
</dependency>
<dependency>
  <groupId>net.sf.jasperreports</groupId>
  <artifactId>jasperreports</artifactId>
  <version>6.20.0</version>
</dependency>
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.2.27</version>
</dependency>
```

---

## 👨‍💻 Autor

Desenvolvido como parte da avaliação prática de **Java Web com JSF**, por Vinícius de Jesus Alves.

---
