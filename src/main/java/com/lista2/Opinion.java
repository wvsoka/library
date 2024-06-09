package com.lista2;

import jakarta.persistence.*;

/**
 * Class representing an opinion which can be added in the library system.
 */
@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double stars;
    private String description;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    private Book bookOpinion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Book getBookOpinion() {
        return bookOpinion;
    }

    public void setBookOpinion(Book bookOpinion) {
        this.bookOpinion = bookOpinion;
    }
}
