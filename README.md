
# 🎬 SerratecFlix API

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square)
![JWT](https://img.shields.io/badge/Auth-JWT-yellow?style=flat-square)
![Swagger](https://img.shields.io/badge/Docs-Swagger%2FOpenAPI-85EA2D?style=flat-square)

### Plataforma de Filmes, Séries e Avaliações

API RESTful desenvolvida com **Java + Spring Boot** para gerenciamento de conteúdo de streaming — cadastro de filmes e séries, avaliações, listas de favoritos com recomendações por IA e autenticação JWT.

</div>

---
# 📖 Sobre o Projeto

O **SerratecFlix** é uma API RESTful completa que simula o backend de uma plataforma de streaming. Permite que usuários avaliem filmes e séries, criem listas personalizadas de favoritos e recebam recomendações geradas pela API do **Groq**.

**A plataforma oferece funcionalidades como:**
-   🎥 Avaliação de filmes e séries
-   ⭐ Favoritar conteúdos
-   📋 Criação de listas personalizadas
-   👀 Gerenciamento de watchlists
-   🏷️ Organização por categorias
-   🔐 Autenticação segura com JWT
-   🌐 Consumo de APIs externas

**Destaques técnicos:**
- Autenticação stateless com JWT e BCrypt
- Paginação e ordenação dinâmica nos endpoints de listagem
- Busca por título e por categoria para filmes e séries
- Recomendações personalizadas via integração com **Groq**
- Banco de dados pré-populado com dados reais via `data.sql`
- Tratamento global de exceções com respostas padronizadas
- Documentação interativa com Swagger / OpenAPI

---

## 🚀 Tecnologias Utilizadas

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.x |
| Persistência | Spring Data JPA + Hibernate |
| Banco de Dados | PostgreSQL |
| Segurança | Spring Security + JWT (jjwt) + BCrypt |
| Documentação | Swagger / OpenAPI 3 |
| Validação | Bean Validation (Jakarta) |
| API Externa | Groq |
| Build | Maven |

## 🛠️ Ferramentas
|||
|---|---|
| Controle de versão (VCS) | Git / GitHub |
|Testes de API | Postman |
| IDE (Ambiente de Desenvolvimento Integrado) | VS Code / IntelliJ IDEA / Eclipse |

---

## 🏗️ Arquitetura do Projeto

O projeto segue arquitetura em camadas com separação clara de responsabilidades:

```
src/main/java/PF/SerratecFlix/
│
├── Config/          # OpenAPI (Swagger) e configurações gerais
├── Controller/      # Endpoints REST
├── Domain/          # Entidades JPA
├── DTO/
│   ├── Request/     # DTOs de entrada (validados com Bean Validation)
│   └── Response/    # DTOs de saída (sem entidades expostas)
├── Enumerated/      # Enums (ClassificacaoIndicativa)
├── Exception/       # Exceções customizadas + @ControllerAdvice
├── Repository/      # Interfaces JPA com Query Methods e JPQL
├── Security/        # JwtService, JwtAuthFilter, UserDetailsServiceImpl
└── Service/         # Regras de negócio
```

---

## 📦 Entidades e Relacionamentos

```
Usuario ────────────────┬──< AvaliacaoFilme >── Filme
        │               └──< AvaliacaoSerie >── Serie
        └──────────────────< ListaFavoritos >──< Filme
                                             └──< Serie

Filme >──────────────────────────────────────── Categoria
Serie >──────────────────────────────────────── Categoria
```

### Detalhamento dos campos

| Entidade | Campos principais |
|---|---|
| **Usuario** | id, nome, email, username (único), senha (BCrypt), fotoPerfil, dataCriacao |
| **Filme** | id, titulo, descricao, duracao (min), dataLancamento, classificacaoIndicativa (ENUM), notaMedia |
| **Serie** | id, titulo, descricao, temporadas, episodios, dataLancamento, notaMedia |
| **Categoria** | id, nome (único), descricao |
| **AvaliacaoFilme** | id, nota (0–10), comentario, dataAvaliacao, usuarioId, filmeId |
| **AvaliacaoSerie** | id, nota (0–10), comentario, dataAvaliacao, usuarioId, serieId |
| **ListaFavoritos** | id, nomeLista, privada (boolean), dataCriacao, usuarioId, filmes[], series[] |

**Classificação Indicativa (ENUM):** `LIVRE` | `DEZ` | `DOZE` | `QUATORZE` | `DEZESSEIS` | `DEZOITO`

---

## 🔐 Segurança

- Rotas públicas: `POST /auth/login` e `POST /usuarios` (cadastro)
- Todas as demais rotas exigem token JWT no header: `Authorization: Bearer <token>`
- Tokens expiram em **8 horas**
- Senhas armazenadas com **BCrypt**

---

## 🌐 Endpoints

### 🔑 Autenticação
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| POST | `/auth/login` | Login — retorna token JWT | ❌ Público |

### 👤 Usuários
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/usuarios` | Listar todos | ✅ |
| GET | `/usuarios/{id}` | Buscar por ID | ✅ |
| POST | `/usuarios` | Cadastrar usuário | ❌ Público |
| PUT | `/usuarios/{id}` | Atualizar usuário | ✅ |
| DELETE | `/usuarios/{id}` | Deletar usuário | ✅ |
| POST | `/usuarios/{id}/foto-perfil` | Adicionar foto (multipart) | ✅ |
| DELETE | `/usuarios/{id}/foto-perfil` | Remover foto | ✅ |

### 🎬 Filmes
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/filmes?page=0&size=10&orderBy=titulo` | Listar paginado | ✅ |
| GET | `/filmes/{id}` | Buscar por ID | ✅ |
| GET | `/filmes/buscar?titulo=` | Buscar por título | ✅ |
| GET | `/filmes/categoria/{categoriaId}` | Buscar por categoria | ✅ |
| POST | `/filmes` | Cadastrar | ✅ |
| PUT | `/filmes/{id}` | Atualizar | ✅ |
| DELETE | `/filmes/{id}` | Deletar | ✅ |

### 📺 Séries
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/series?page=0&size=10&orderBy=titulo` | Listar paginado | ✅ |
| GET | `/series/{id}` | Buscar por ID | ✅ |
| GET | `/series/categoria/{categoriaId}` | Buscar por categoria | ✅ |
| POST | `/series` | Cadastrar | ✅ |
| PUT | `/series/{id}` | Atualizar | ✅ |
| DELETE | `/series/{id}` | Deletar | ✅ |

### 🏷️ Categorias
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/categorias` | Listar todas | ✅ |
| GET | `/categorias/{id}` | Buscar por ID | ✅ |
| POST | `/categorias` | Cadastrar | ✅ |
| PUT | `/categorias/{id}` | Atualizar | ✅ |
| DELETE | `/categorias/{id}` | Deletar | ✅ |

### ⭐ Avaliações de Filmes
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/avaliacoes_filme` | Listar todas | ✅ |
| GET | `/avaliacoes_filme/{id}` | Buscar por ID | ✅ |
| POST | `/avaliacoes_filme` | Cadastrar | ✅ |
| PUT | `/avaliacoes_filme/{id}` | Atualizar | ✅ |
| DELETE | `/avaliacoes_filme/{id}` | Deletar | ✅ |

### 💫 Avaliações de Séries
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/avaliacaoserie` | Listar todas | ✅ |
| GET | `/avaliacaoserie/{id}` | Buscar por ID | ✅ |
| POST | `/avaliacaoserie` | Cadastrar | ✅ |
| PUT | `/avaliacaoserie/{id}` | Atualizar | ✅ |
| DELETE | `/avaliacaoserie/{id}` | Deletar | ✅ |

### 📋 Listas de Favoritos
| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/listas` | Listar todas | ✅ |
| GET | `/listas/{id}` | Buscar por ID | ✅ |
| POST | `/listas` | Cadastrar | ✅ |
| PUT | `/listas/{id}` | Atualizar | ✅ |
| DELETE | `/listas/{id}` | Deletar | ✅ |
| GET | `/listas/{id}/recomendacoes` | Recomendações via Groq | ✅ |

---

## ⚠️ Tratamento de Exceções

Todas as exceções são tratadas globalmente via `@ControllerAdvice` com respostas padronizadas:

```json
{
  "status": 404,
  "titulo": "Recurso não encontrado.",
  "dataHora": "2026-05-27T14:00:00",
  "erros": ["Filme com ID ... não encontrado"]
}
```

| Código | Situação |
|---|---|
| 400 | Dados inválidos / validação falhou |
| 401 | Não autenticado |
| 404 | Recurso não encontrado |
| 409 | Conflito (ex: categoria com nome duplicado) |

---

## ⚙️ Como Executar

### Pré-requisitos
- Java 17+
- PostgreSQL 14+
- Maven 3.8+

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/SerratecFlix.git
cd SerratecFlix
```

### 2. Crie o banco de dados
```sql
CREATE DATABASE serratecflix;
```

### 3. Configure o `application.properties`
```properties
# Banco de dados
spring.datasource.url=jdbc:postgresql://localhost:5432/serratecflix
spring.datasource.username=postgres
spring.datasource.password=sua_senha

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Popular banco automaticamente na primeira execução
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql
spring.jpa.defer-datasource-initialization=true

# Groq
groq.api.key=SUA_CHAVE_AQUI
```

### 4. Execute
```bash
mvn spring-boot:run
```

### 5. Acesse a documentação
```
http://localhost:8080/swagger-ui/index.html
```

> O banco já inicia pré-populado com 10 categorias, 10 filmes, 10 séries, 10 usuários, avaliações e listas de favoritos.

---

## 📚 Como usar a API (fluxo básico)

```bash
# 1. Crie um usuário (rota pública)
POST /usuarios
{ "nome": "...", "email": "...", "username": "...", "senha": "..." }

# 2. Faça login e guarde o token
POST /auth/login
{ "email": "...", "senha": "..." }
→ { "token": "eyJ...", "email": "...", "nome": "..." }

# 3. Use o token nas demais requisições
Authorization: Bearer eyJ...

# 4. Liste filmes paginados
GET /filmes?page=0&size=10&orderBy=titulo

# 5. Crie uma lista de favoritos
POST /listas
{ "nomeLista": "...", "privada": false, "usuarioId": "uuid", "filmes": [], "series": [] }

# 6. Obtenha recomendações com IA
GET /listas/{id}/recomendacoes
```

---

## 👩‍💻 Integrantes

| Nome | Responsabilidade | Parte Individual | GitHub | LinkedIn |
|---|---|---|---|---|
| **Adriane Tranhaqui** | Série, Avaliação Série e Segurança | Paginação série | [GitHub](https://github.com/AdrianeTranhaqui) | [LinkedIn](https://www.linkedin.com/in/adriane-tranhaqui-356806353/) |
| **Ana Luísa da Cunha Reis** | API Externa, Entidades, Lista Favorito, Documentação e Inserção dos dados no banco | Envio de email | [GitHub](https://github.com/devAnaLuX) | [LinkedIn](https://www.linkedin.com/in/ana-luisa-cunha-reis-8baa563a3/) |
| **Melissa Lima** | Categoria e Avaliação Filme | Filtro avançado JQPL | [GitHub](https://github.com/melissa-lima21) | [LinkedIn](https://www.linkedin.com/in/melissa-lima-0628a324b/) |
| **Raquel Taveira** | Usuário | Inserção de imagem | [GitHub](https://github.com/Raquel-Beep) | [LinkedIn](https://www.linkedin.com/in/raquel-taveira-02668423b/) |
| **Vitória Zanchet** | Filme e Front-end | Paginação filme | [GitHub](https://github.com/vitoriazanchet) | [LinkedIn](https://www.linkedin.com/in/vitoria-zanchet) |

---

## 📄 Licença

Projeto desenvolvido para fins educacionais no programa **Serratec** — Residência em Tecnologia da Informação.

---

# 💡 Considerações Finais

[](https://github.com/devAnaLuX/TRABALHO-FINAL---API#-considerações-finais)

O **SerratecFlix** foi desenvolvido aplicando conceitos reais de desenvolvimento backend com foco em:

-   Arquitetura em camadas
-   Persistência de dados
-   Segurança com JWT
-   Tratamento de exceções
-   Organização profissional de código
-   APIs RESTful
-   Boas práticas com Spring Boot

🚀 Projeto desenvolvido para aprendizado e evolução técnica.
