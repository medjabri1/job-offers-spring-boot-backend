package com.mjrdev.JobOffers.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="postulations")
public class Postulation {

    // ATTRIBUTES

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="applier_id", referencedColumnName = "id")
    private User applier;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="offer_id", referencedColumnName = "id")
    private Offer offer;

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

    public User getApplier() {
        return applier;
    }

    public void setApplier(User applier) {
        this.applier = applier;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer_id) {
        this.offer = offer_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
