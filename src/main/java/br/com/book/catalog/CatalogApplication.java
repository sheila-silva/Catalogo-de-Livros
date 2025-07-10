package br.com.book.catalog;

import br.com.book.catalog.client.GutendexClient;
import br.com.book.catalog.dto.BookDto;
import br.com.book.catalog.dto.GutendexResponseDto;
import br.com.book.catalog.model.BookEntity;
import br.com.book.catalog.model.AuthorEntity;
import br.com.book.catalog.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class CatalogApplication implements CommandLineRunner {

    private final GutendexClient client = new GutendexClient();
    private final BookService bookService;

    public CatalogApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== Catálogo de Livros =====");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar todos os livros salvos");
            System.out.println("3 - Listar livros por idioma");
            System.out.println("4 - Listar autores salvos");
            System.out.println("5 - Listar autores vivos em determinado ano");
            System.out.println("6 - Listar Top 10 livros mais baixados");
            System.out.println("7 - Buscar autores por nome");
            System.out.println("8 - Buscar autores por ano de nascimento ou falecimento");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1 -> buscarLivro(scanner);
                    case 2 -> listarTodosLivros();
                    case 3 -> listarPorIdioma();
                    case 4 -> listarAutores();
                    case 5 -> listarAutoresVivos(scanner);
                    case 6 -> listarTop10MaisBaixados();
                    case 7 -> buscarAutoresPorNome(scanner);
                    case 8 -> buscarAutoresPorAno(scanner);
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número.");
                opcao = -1;
            }

        } while (opcao != 0);

        scanner.close();
    }

    private void buscarLivro(Scanner scanner) {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        GutendexResponseDto response = client.buscarLivroPorTitulo(titulo);

        if (response != null && response.results() != null && !response.results().isEmpty()) {
            BookDto bookDto = response.results().get(0);

            String nomeAutor = bookDto.authors().isEmpty() ? "Desconhecido" : bookDto.authors().get(0).name();
            String idioma = bookDto.languages().isEmpty() ? "Desconhecido" : bookDto.languages().get(0);
            int downloads = bookDto.downloadCount() != null ? bookDto.downloadCount() : 0;

            System.out.println("\nTítulo: " + bookDto.title());
            System.out.println("Autor: " + nomeAutor);
            System.out.println("Idioma: " + idioma);
            System.out.println("Downloads: " + downloads);

            System.out.print("Deseja salvar este livro no catálogo? (s/n): ");
            String salvar = scanner.nextLine();

            if (salvar.equalsIgnoreCase("s")) {
                BookEntity salvo = bookService.salvarLivroComAutor(bookDto);
                if (salvo != null) {
                    System.out.println("✅ Livro e autor salvos no banco de dados com sucesso.");
                } else {
                    System.out.println("⚠️ Este livro já está salvo no banco de dados.");
                }
            } else {
                System.out.println("Livro não foi salvo.");
            }

        } else {
            System.out.println("Nenhum livro encontrado com esse título.");
        }
    }

    private void listarTodosLivros() {
        var livros = bookService.buscarTodosLivros();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado no banco de dados.");
            return;
        }

        System.out.println("\n===== Lista de Livros Salvos =====");
        for (var livro : livros) {
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + (livro.getAutor() != null ? livro.getAutor().getName() : "Desconhecido"));
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Downloads: " + livro.getDownloads());
            System.out.println("------------------------");
        }
    }

    private void listarPorIdioma() {
        List<String> idiomas = Arrays.asList("pt", "en", "fr");

        Map<String, Long> contagem = bookService.contarLivrosPorIdiomas(idiomas);

        System.out.println("\n===== Quantidade de livros por idioma (no banco de dados) =====");
        contagem.forEach((idioma, quantidade) ->
                System.out.printf("Idioma: %s -> %d livros%n", idioma, quantidade)
        );
    }

    private void listarAutores() {
        var autores = bookService.buscarTodosAutores();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado no banco de dados.");
            return;
        }

        System.out.println("\n===== Lista de Autores Salvos =====");
        for (var autor : autores) {
            System.out.println("Nome: " + autor.getName());
            System.out.println("Nascimento: " + (autor.getBirthYear() != null ? autor.getBirthYear() : "Desconhecido"));
            System.out.println("Falecimento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "Desconhecido"));
            System.out.println("------------------------");
        }
    }

    private void listarAutoresVivos(Scanner scanner) {
        System.out.print("Digite o ano para verificar autores vivos: ");
        int ano;
        try {
            ano = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
            return;
        }

        var autoresVivos = bookService.buscarAutoresVivosNoAno(ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano);
        } else {
            System.out.println("\n===== Autores vivos em " + ano + " =====");
            for (var autor : autoresVivos) {
                System.out.println("Nome: " + autor.getName());
                System.out.println("Nascimento: " + autor.getBirthYear());
                System.out.println("Falecimento: " + autor.getDeathYear());
                System.out.println("------------------------");
            }
        }
    }

    private void listarTop10MaisBaixados() {
        GutendexResponseDto response = client.buscarTopLivrosMaisBaixados();

        if (response != null && response.results() != null && !response.results().isEmpty()) {
            List<BookDto> top10 = response.results().stream()
                    .sorted(Comparator.comparing(BookDto::downloadCount).reversed())
                    .limit(10)
                    .toList();

            System.out.println("\n===== Top 10 Livros Mais Baixados =====");
            for (BookDto book : top10) {
                String autor = book.authors().isEmpty() ? "Desconhecido" : book.authors().get(0).name();
                String idioma = book.languages().isEmpty() ? "Desconhecido" : book.languages().get(0);
                int downloads = book.downloadCount() != null ? book.downloadCount() : 0;

                System.out.println("Título: " + book.title());
                System.out.println("Autor: " + autor);
                System.out.println("Idioma: " + idioma);
                System.out.println("Downloads: " + downloads);
                System.out.println("------------------------");
            }
        } else {
            System.out.println("Não foi possível recuperar os dados da API.");
        }
    }

    private void buscarAutoresPorNome(Scanner scanner) {
        System.out.print("Digite o nome (ou parte do nome) do autor: ");
        String nome = scanner.nextLine().trim();

        List<AuthorEntity> autores = bookService.buscarAutoresPorNome(nome);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado com esse nome.");
        } else {
            System.out.println("\n===== Resultado da busca por autores =====");
            for (AuthorEntity autor : autores) {
                System.out.println("Nome: " + autor.getName());
                System.out.println("Nascimento: " + (autor.getBirthYear() != null ? autor.getBirthYear() : "Desconhecido"));
                System.out.println("Falecimento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "Desconhecido"));

                List<BookEntity> livrosDoAutor = bookService.buscarLivrosPorAutor(autor);
                if (!livrosDoAutor.isEmpty()) {
                    System.out.println("Livros salvos no banco:");
                    for (BookEntity livro : livrosDoAutor) {
                        System.out.println("  - " + livro.getTitulo());
                    }
                } else {
                    System.out.println("Nenhum livro salvo para este autor.");
                }
                System.out.println("------------------------");
            }
        }
    }

    private void buscarAutoresPorAno(Scanner scanner) {
        System.out.print("Digite o ano (nascimento ou falecimento): ");
        int ano;
        try {
            ano = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido.");
            return;
        }

        List<AuthorEntity> autores = bookService.buscarAutoresPorAnoNascimentoOuFalecimento(ano);

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor encontrado com ano de nascimento ou falecimento igual a " + ano);
        } else {
            System.out.println("\n===== Autores encontrados para o ano " + ano + " =====");
            for (AuthorEntity autor : autores) {
                System.out.println("Nome: " + autor.getName());
                System.out.println("Nascimento: " + (autor.getBirthYear() != null ? autor.getBirthYear() : "Desconhecido"));
                System.out.println("Falecimento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "Desconhecido"));

                List<BookEntity> livros = bookService.buscarLivrosPorAutor(autor);
                if (!livros.isEmpty()) {
                    System.out.println("Livros salvos no banco:");
                    for (BookEntity livro : livros) {
                        System.out.println("  - " + livro.getTitulo());
                    }
                } else {
                    System.out.println("Nenhum livro salvo para este autor.");
                }
                System.out.println("------------------------");
            }
        }
    }
}
