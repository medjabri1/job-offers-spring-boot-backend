package com.mjrdev.JobOffers.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="categories")
public class Category {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 200)
    private String name;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd 'T'HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // GETTERS + SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
