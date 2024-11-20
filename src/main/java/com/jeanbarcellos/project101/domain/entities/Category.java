package com.jeanbarcellos.project101.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.jeanbarcellos.core.domain.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
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
    public void generateCreatedAt() {
        var dateTimeNow = LocalDateTime.now();
        this.createdAt = dateTimeNow;
        this.updatedAt = dateTimeNow;
    }

    @PreUpdate
    public void generateUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}