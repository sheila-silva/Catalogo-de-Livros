package br.com.book.catalog.repository;

import br.com.book.catalog.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    long countByIdiomaIgnoreCase(String idioma);

    boolean existsByTituloIgnoreCaseAndIdiomaIgnoreCaseAndAutor_NameIgnoreCase(
            String titulo, String idioma, String nomeAutor
    );
}
