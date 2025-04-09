package com.postpaf.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private Integer idUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contenu", nullable = false)
    private String contenu;

    @Column(name = "creationdate", nullable = false)
    private LocalDateTime creationDate;

    // Constructeurs
    public Post() {
    }

    public Post(Integer idUser, String title, String contenu) {
        this.idUser = idUser;
        this.title = title;
        this.contenu = contenu;
        this.creationDate = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", title='" + title + '\'' +
                ", contenu='" + contenu + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
