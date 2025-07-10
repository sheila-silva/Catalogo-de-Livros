package br.com.book.catalog.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private int downloads;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private AuthorEntity autor;

    public BookEntity() {}

    public BookEntity(String titulo, String idioma, int downloads, AuthorEntity autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.downloads = downloads;
        this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }
    public int getDownloads() { return downloads; }
    public AuthorEntity getAutor() { return autor; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setDownloads(int downloads) { this.downloads = downloads; }
    public void setAutor(AuthorEntity autor) { this.autor = autor; }
}
