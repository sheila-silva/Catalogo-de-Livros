package br.com.book.catalog.service;

import br.com.book.catalog.dto.AuthorDto;
import br.com.book.catalog.dto.BookDto;
import br.com.book.catalog.model.AuthorEntity;
import br.com.book.catalog.model.BookEntity;
import br.com.book.catalog.repository.AuthorRepository;
import br.com.book.catalog.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookEntity salvarLivroComAutor(BookDto bookDto) {
        AuthorDto authorDto = bookDto.authors().isEmpty() ? null : bookDto.authors().get(0);

        AuthorEntity autor = null;
        if (authorDto != null) {
            Optional<AuthorEntity> existente = authorRepository.findByNameIgnoreCase(authorDto.name());
            autor = existente.orElseGet(() -> {
                AuthorEntity novo = new AuthorEntity(authorDto.name(), authorDto.birthYear(), authorDto.deathYear());
                return authorRepository.save(novo);
            });
        }

        String titulo = bookDto.title();
        String idioma = bookDto.languages().isEmpty() ? "Desconhecido" : bookDto.languages().get(0);
        String nomeAutor = autor != null ? autor.getName() : "Desconhecido";

        boolean existe = bookRepository.existsByTituloIgnoreCaseAndIdiomaIgnoreCaseAndAutor_NameIgnoreCase(
                titulo, idioma, nomeAutor
        );

        if (existe) {
            return null;
        }

        BookEntity book = new BookEntity(titulo, idioma, 0, autor);
        return bookRepository.save(book);
    }

    public Map<String, Long> contarLivrosPorIdiomas(List<String> idiomas) {
        return idiomas.stream()
                .collect(Collectors.toMap(
                        idioma -> idioma,
                        idioma -> bookRepository.countByIdiomaIgnoreCase(idioma)
                ));
    }

    public List<BookEntity> buscarTodosLivros() {
        return bookRepository.findAll();
    }

    public List<AuthorEntity> buscarTodosAutores() {
        return authorRepository.findAll();
    }

    public List<AuthorEntity> buscarAutoresVivosNoAno(int ano) {
        return authorRepository.findAll().stream()
                .filter(a -> a.getBirthYear() != null && a.getBirthYear() <= ano &&
                        (a.getDeathYear() == null || a.getDeathYear() >= ano))
                .toList();
    }

    public List<AuthorEntity> buscarAutoresPorNome(String nome) {
        return authorRepository.findByNameContainingIgnoreCase(nome);
    }

    public List<BookEntity> buscarLivrosPorAutor(AuthorEntity autor) {
        return bookRepository.findAll().stream()
                .filter(livro -> livro.getAutor() != null && livro.getAutor().equals(autor))
                .toList();
    }


    public List<AuthorEntity> buscarAutoresPorAnoNascimentoOuFalecimento(int ano) {
        return authorRepository.findAll().stream()
                .filter(a -> (a.getBirthYear() != null && a.getBirthYear() == ano)
                        || (a.getDeathYear() != null && a.getDeathYear() == ano))
                .toList();
    }
}
