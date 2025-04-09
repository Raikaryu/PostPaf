package com.postpaf.Dtos;

import java.time.LocalDateTime;

public class PostDTO {

    private Long id;
    private Integer idUser;
    private String userPseudo;
    private String title;
    private String contenu;
    private LocalDateTime creationDate;

    // Constructeur pour la création
    public static class PostCreateDTO {
        private Integer idUser;
        private String title;
        private String contenu;

        public PostCreateDTO() {
        }

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
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
    }

    // Constructeur pour la mise à jour
    public static class PostUpdateDTO {
        private String title;
        private String contenu;

        public PostUpdateDTO() {
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
    }

    // Constructeurs, getters et setters pour PostDTO
    public PostDTO() {
    }

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

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
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
}
