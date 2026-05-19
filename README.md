Mano, o que aconteceu aí foi que na hora de você copiar e colar o texto anterior, o bloco de código do "Passo 1" ficou sem o fechamento (as três crases `````), o que acabou "engolindo" todo o resto da formatação para baixo e transformando os links e títulos em texto bagunçado.

Eu ajustei 100% da formatação. Só clicar no botão de **"Copiar código"** (Copy code) no canto superior direito desse bloco preto aqui embaixo e colar direto no seu arquivo `README.md` que vai ficar perfeito:

```markdown
# VemPraArena 🏟️

O **VemPraArena** é uma plataforma GovTech desenvolvida para conectar os cidadãos à programação da Arena Pernambuco, facilitando a descoberta de eventos e otimizando a ocupação do equipamento público.

---

## 🚀 Visão do Projeto
O projeto visa reduzir a subutilização da Arena Pernambuco através de uma solução tecnológica de alta visibilidade. A plataforma atua como uma ponte entre a infraestrutura pública e a população, incentivando a participação em eventos esportivos, culturais e corporativos.

---

## 🛠️ Tecnologias Utilizadas

### Backend (POO & API REST)
* **Java 25**: Linguagem principal para modelagem Orientada a Objetos e lógica de negócio.
* **Spring Boot (v4.0.0)**: Framework para construção da API REST robusta e escalável.
* **Spring Security**: Gestão de autenticação, controle de acessos de rotas e proteção do sistema.
* **BCrypt (PasswordEncoder)**: Mecanismo de hash seguro para criptografia de senhas antes do armazenamento.
* **Spring Data JPA & Hibernate**: Camada de persistência e mapeamento objeto-relacional (ORM).
* **PostgreSQL (via Supabase)**: Banco de dados relacional hospedado em nuvem, contando com criptografia nativa de dados em repouso (AES-256).
* **Maven**: Automação de build e gerenciamento de dependências.

### Frontend
* **HTML5 / JavaScript**: Roteamento lógico estrutural e consumo assíncrono da API via chamadas `fetch` com payloads em **JSON puro**.
* **Tailwind CSS**: Estilização visual moderna baseada no conceito estético Neo-Brutalista.

---

## ⚙️ Como Rodar o Projeto Localmente

Siga o passo a passo abaixo para configurar o ambiente de desenvolvimento e executar a aplicação.

### 📋 Pré-requisitos
* **Git** instalado na máquina.
* **Java Development Kit (JDK) 25** instalado e configurado nas variáveis de ambiente.
* Uma IDE de preferência (VS Code, IntelliJ IDEA, Eclipse).
* Extensão **Live Server** instalada na IDE (altamente recomendado para execução do frontend).

### Passo 1: Clonar o Repositório
Abra o terminal do seu sistema e execute os seguintes comandos:
```bash
git clone https://github.com/SEU_USUARIO/vem-pra-arena.git
cd vem-pra-arena

```

### Passo 2: Configurar e Executar o Backend

Por motivos de segurança e boas práticas de desenvolvimento, as credenciais físicas de acesso ao banco de dados não estão expostas no código-fonte. Elas devem ser injetadas dinamicamente via variáveis de ambiente no momento da execução.

1. Navegue até a pasta do módulo do backend:

```bash
cd plataforma

```

2. Configure as variáveis de ambiente locais no seu terminal (substitua os valores de exemplo pelas credenciais privadas fornecidas pela equipe de desenvolvimento):

**No Windows (PowerShell):**

```powershell
$env:DB_URL="jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres"
$env:DB_USERNAME="INSIRA_O_USUARIO_AQUI"
$env:DB_PASSWORD="INSIRA_A_SENHA_AQUI"

```

**No Linux / macOS (Terminal):**

```bash
export DB_URL="jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres"
export DB_USERNAME="INSIRA_O_USUARIO_AQUI"
export DB_PASSWORD="INSIRA_A_SENHA_AQUI"

```

3. Execute o servidor Spring Boot utilizando o Maven Wrapper da raiz do backend:

```bash
.\mvnw.cmd spring-boot:run

```

*O servidor backend será inicializado com sucesso e estará ouvindo requisições na porta local padrão `8080` (http://localhost:8080).*

**Executar o Backend pelo VS Code (Tecla F5)**

A forma mais prática e rápida de rodar o projeto é utilizando a configuração nativa do VS Code através do arquivo de inicialização de ambiente (launch.json).

Certifique-se de que a pasta .vscode existe na raiz do projeto (ou dentro da pasta plataforma).

Abra o arquivo principal do backend (Application.java) localizado no pacote com.vempraarena.plataforma.

Pressione a tecla F5 (ou clique em Run/Debug) para compilar e iniciar o servidor. O backend estará ativo e ouvindo requisições na porta local.

### Passo 3: Executar o Frontend

Com o ecossistema do backend ativo e comunicando-se com o banco de dados:

1. Retorne ou abra a pasta raiz onde se encontram as telas da interface.
2. Inicie o arquivo `index.html` ou `auth_business.html` através do **Live Server** (ou abra diretamente no navegador de sua escolha).
3. O frontend passará a interceptar os eventos de formulário, convertendo-os em objetos textuais e enviando requisições REST puras diretamente para a API local.

---

## 🔐 Credenciais Homologadas para Testes (Admin)

Para facilitar a avaliação das funcionalidades administrativas pelos professores e revisores da disciplina, uma credencial de teste com privilégios de Administrador foi pré-configurada e injetada com segurança (senha criptografada) na base de dados da aplicação:

* **Login Administrativo:** `GOV-2026-9918`
* **Senha de Acesso:** `odeiosupabase`

---

## 📂 Planejamento, User Stories e Prototipagem

O desenvolvimento do ecossistema e das regras de negócio foi amplamente mapeado e estruturado com foco em metodologias ágeis e arquitetura de software:

### 👥 Histórias de Usuário & Backlog

O acompanhamento de sprints, requisitos técnicos corporativos e histórias de usuário (User Stories) pode ser visualizado no link oficial de gerência:

* 📌 **[Acesse o Backlog do Projeto no Trello](https://trello.com/invite/b/69ebb89ff60338739439751a/ATTI8c6dac8a5fb8d299770cabf2631b0ca312555C19/projetos-3-vempraarena)**

### 🎨 Prototipagem Lo-Fi (Design System)

O fluxo de telas de baixa fidelidade e a identidade visual neo-brutalista aplicada ao projeto foram validados no Figma:

* 📌 **[Acesse o Protótipo de Interface no Figma](https://www.figma.com/proto/VjPOLNdwJL7iIhuIHU6qJJ/VemPraArena?node-id=30-317&t=xqTDZgPZaEAn8JhZ-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1&starting-point-node-id=1%3A2)**

### 🎬 Demonstração Prática (Screencasts)

Os fluxos de navegação completos e as integrações validadas ponta a ponta estão disponíveis em formato de vídeo no canal do projeto:

* 🎥 **[Assista aos Screencasts das Funcionalidades no YouTube](https://www.youtube.com/@VEMPRAARENA)**

---

## 👥 Equipe de Desenvolvimento

* **Eduardo Henrique**
* **Cauã dos Santos**
* **Luiz Henrique**
* **Pedro Vinicius**
* **Pedro Marrocos**
* **Paulo Marrocos**

---

*Este projeto compõe o portfólio e a avaliação oficial da disciplina de Programação Orientada a Objetos (POO).*

```

```
