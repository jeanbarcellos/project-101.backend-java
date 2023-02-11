package com.jeanbarcellos.demo.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.jeanbarcellos.core.domain.EntityBase;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Entity
@Accessors(chain = true)
@Table(name = "category")
public class Category extends EntityBase {

    @Column(name = "name", nullable = false)
    private String name;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(UUID id, String name) {
        this.id = id;
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