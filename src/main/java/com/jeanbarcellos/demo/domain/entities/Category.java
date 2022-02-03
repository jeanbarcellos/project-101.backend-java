package com.jeanbarcellos.demo.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.jeanbarcellos.demo.core.domain.AggregateRoot;
import com.jeanbarcellos.demo.core.domain.Entity;

import lombok.Getter;

@javax.persistence.Entity
@Getter
@Table(name = "category")
public class Category extends Entity implements AggregateRoot {

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public LocalDateTime updatedAt;

    public Category(String name) {
        this.name = name;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}