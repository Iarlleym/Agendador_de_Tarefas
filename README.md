# 🗓️ Agendador de Tarefas - Microserviço

Este microserviço é responsável pelo **cadastro, atualização, exclusão e consulta de tarefas**.  
Ele está totalmente integrado com o microserviço de **Cadastro de Usuários**, utilizando **FeignClient** para autenticação e obtenção de informações do usuário logado.

---

## 🛠️ Tecnologias Utilizadas
<div style="display: inline_block"><br>
  <img align="center" alt="Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-plain.svg">
  <img align="center" alt="Spring Boot" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg">
  <img align="center" alt="MongoDB" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mongodb/mongodb-plain.svg">
</div>

---

## 📂 Estrutura do Projeto

- `controller`: Endpoints REST para CRUD de tarefas  
- `business`: Serviços que contêm a **lógica de negócio**  
- `business/mapper`: Conversões **DTO ↔ Entity** usando **MapStruct**  
- `infrastructure/entity`: Representação das entidades no MongoDB  
- `infrastructure/repository`: Repositórios Spring Data MongoDB  
- `infrastructure/enums`: Enumerações de status das tarefas  
- `infrastructure/client`: Comunicação com o microserviço de usuários via **FeignClient**  
- `infrastructure/security`: Configurações de segurança e JWT  

---

## ⚙️ Funcionalidades

- Criar, listar, atualizar e deletar tarefas  
- Consultar tarefas por período ou por usuário logado  
- Alterar status de notificação das tarefas (PENDENTE, NOTIFICADO, CANCELADO)  
- Comunicação direta com o **Cadastro de Usuários** para validar e obter informações do usuário logado  

---

## 💻 Como Rodar o Projeto

1. Clone o repositório:  
```bash
git clone https://github.com/Iarlleym/Agendador_de_Tarefas.git
