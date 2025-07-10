# 📚 Catálogo de Livros

Este projeto é um **catálogo de livros** que permite consultar obras por meio da **API Gutendex** e armazená-las em um banco de dados **PostgreSQL**.  
Ele foi desenvolvido como parte do **Desafio Tech Foundation ONE G8 BR**, atendendo aos requisitos obrigatórios e extras do desafio.

## ✅ Funcionalidades principais

- 🔍 Buscar livros por título via API
- 📘 Listar todos os livros registrados no banco de dados
- 🖊️ Listar autores salvos
- 📅 Listar autores vivos em determinado ano
- 🌍 Listar livros por idioma: **inglês**, **francês** e **português**

## ✨ Funcionalidades extras implementadas

- 🔝 Listar os **10 livros mais baixados** da API Gutendex
- 👤 Buscar obras por **nome do autor**
- 📆 Buscar autores por **ano de nascimento ou falecimento**

## 🧩 Estrutura do projeto

O projeto é composto por:

- Código Java moderno com orientação a objetos
- DTOs para mapear dados da API
- Entidades JPA para persistência no banco de dados
- Repositórios com Spring Data JPA (com *derived queries*)
- Camada de serviço com a lógica de negócios
- Configuração de banco de dados via `application.properties`
- Interface interativa via console (menu de opções)

## 🛠️ Tecnologias utilizadas

- 📦 **Spring Boot**
- 🌐 **HttpClient (Java 11+)**
- 🔃 **Jackson (ObjectMapper)** 
- 🗄️ **Spring Data JPA**
- 🐘 **PostgreSQL**
- 📚 **Gutendex API** 

-------------------------------------------------------------------------------------------------
# 📚 Book Catalog

This project is a **book catalog** that allows users to search for literary works using the **Gutendex API** and store them in a **PostgreSQL** database.  
It was developed as part of the **Tech Foundation ONE G8 BR Challenge**, meeting both the core and extra requirements.

## ✅ Main Features

- 🔍 Search books by title using the API  
- 📘 List all books stored in the database  
- 🖊️ List registered authors  
- 📅 List authors who were alive in a given year  
- 🌍 List books by language: **English**, **French**, and **Portuguese**

## ✨ Extra Features Implemented

- 🔝 List the **top 10 most downloaded books** from the Gutendex API  
- 👤 Search books by **author name**  
- 📆 Search authors by **year of birth or death**



