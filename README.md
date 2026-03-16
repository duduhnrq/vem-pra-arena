# VemPraArena 🏟️

O **VemPraArena** é uma plataforma GovTech desenvolvida para conectar os cidadãos à programação da Arena Pernambuco, facilitando a descoberta de eventos e otimizando a ocupação do equipamento público.

---

## 🚀 Visão do Projeto
O projeto visa reduzir a subutilização da Arena Pernambuco através de uma solução tecnológica de alta visibilidade. A plataforma atua como uma ponte entre a infraestrutura pública e a população, incentivando a participação em eventos esportivos, culturais e corporativos.

## 🛠️ Tecnologias Utilizadas

### Backend (POO)
* **Java 21+**: Linguagem para modelagem de objetos e lógica de negócio.
* **Spring Boot**: Framework para construção da aplicação web.
* **Maven**: Gerenciamento de dependências.

### Frontend
* **HTML5 / JavaScript**: Estrutura e interatividade.
* **Tailwind CSS**: Estilização baseada no conceito Neo-Brutalista.

---

## 📂 Entrega 01: Planejamento, User Stories e Prototipagem

Esta entrega foca na definição dos requisitos de negócio e na experiência do usuário (UX) através de protótipos de baixa fidelidade.

### 👥 Histórias de Usuário & Critérios de Aceitação (BDD)

Abaixo estão listadas as histórias que guiam o desenvolvimento do sistema, detalhando o comportamento esperado em cada cenário.

#### US01: Destaque de Notícias (Marquee)
* **Descrição:** Como torcedor, quero ver avisos rápidos no topo do site para me manter informado.
* **Cenário:**
   * **Dado** que estou na home;
   * **Quando** olho para o topo;
   * **Então** vejo uma barra preta com textos correndo sobre os próximos jogos e shows.

#### US02: Catálogo de Eventos
* **Descrição:** Como usuário, quero ver os eventos na página inicial para saber o que está acontecendo na Arena.
* **Cenário:**
  * **Dado** que acesso o site;
  * **Quando** a página carrega;
  * **Então** vejo os cards de eventos com título, data e botão de ação.

#### US03: Busca por Nome
* **Descrição:** Como usuário, quero digitar o nome de um time na busca para achar o evento mais rápido.
* **Cenário:**
    * **Dado** que uso o campo de busca;
    * **Quando** digito "Sport";
    * **Então** o sistema filtra os eventos relacionados.

#### US04: Menu de Navegação
* **Descrição:** Como usuário, quero usar o menu do topo para pular direto para a seção de agenda.
* **Cenário:**
    * **Dado** que estou no topo da página;
    * **Quando** clico em "Eventos";
    * **Então** a página desliza automaticamente para a lista de cards.

#### US05: Autoadentimento (FAQ)
* **Descrição:** Como usuário com dúvidas, quero ler as perguntas frequentes diretamente no site, sem precisar entrar em contato.
* **Cenário:**
    * **Dado** que estou na seção de dúvidas;
    * **Quando** clico em uma pergunta;
    * **Então** a resposta se expande logo abaixo.

#### US06: Acesso ao Checkout
* **Descrição:** Como interessado, quero clicar no botão de um evento para iniciar minha reserva.
* **Cenário:**
    * **Dado** que escolhi um evento no card;
    * **Quando** clico em "Reservar Ingresso";
    * **Então** sou redirecionado para a página de formulário.

#### US07: Identificação Básica
* **Descrição:** Como comprador, quero preencher meu nome e e-mail para reservar meu lugar.
* **Cenário:**
    * **Dado** que estou na tela de reserva;
    * **Quando** preencho o formulário;
    * **Então** clico no botão para confirmar meu pedido.

### 🎨 Prototipagem Lo-Fi (Figma)
O protótipo de baixa fidelidade foca na estrutura da informação e no fluxo de navegação, utilizando a estética Neo-Brutalista definida no Styleguide.

* 📌 **[Acesse aqui o Protótipo de Baixa Fidelidade](https://www.figma.com/proto/VjPOLNdwJL7iIhuIHU6qJJ/VemPraArena?node-id=30-317&t=xqTDZgPZaEAn8JhZ-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1&starting-point-node-id=1%3A2)**

### 🎬 Apresentação (Screencast)
No vídeo abaixo, apresentamos o fluxo de navegação contemplando as 5 principais histórias de usuário (US01 a US05).

* 🎥 **[Assista ao vídeo no YouTube](https://www.youtube.com/watch?v=bpGE4QOb8ws)**

---

## 👥 Equipe
* **Eduardo Henrique**
* **Cauã dos Santos**
* **Luiz Henrique**
* **Pedro Vinicius**
* **Pedro Marrocos**
* **Paulo Marrocos**

---
*Este projeto compõe a documentação da disciplina de Programação Orientada a Objetos (POO).*
