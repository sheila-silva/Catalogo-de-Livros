package br.com.book.catalog.repository;

import br.com.book.catalog.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByNameIgnoreCase(String name);

    List<AuthorEntity> findByNameContainingIgnoreCase(String name);
}
