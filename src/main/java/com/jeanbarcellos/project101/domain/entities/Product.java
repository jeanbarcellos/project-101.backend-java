package com.jeanbarcellos.project101.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.jeanbarcellos.core.domain.EntityBase;
import com.jeanbarcellos.core.exception.DomainException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Builder
@Accessors(chain = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "product")
public class Product extends EntityBase {

    public static final Integer QUANTITY_EMPTY = 0;
    public static final Integer QUANTITY_MIN_ADD = 1;
    public static final Integer QUANTITY_MAX_ADD = Integer.MAX_VALUE;

    @Type(type = "uuid-char")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "product_category_id_fk"), nullable = false)
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "image", nullable = true)
    private String image;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Builder.Default
    @Column(name = "quantity", nullable = false)
    private Integer quantity = QUANTITY_EMPTY;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected Product() {
    }

    public Product(Category category, String name, String description, String image,
            Boolean active, BigDecimal value) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.image = image;
        this.active = active;
        this.value = value;
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

    public Product activate() {
        this.active = true;
        return this;
    }

    public Product inactivate() {
        this.active = false;
        return this;
    }

    public boolean hasQuantity(Integer quantity) {
        return this.quantity >= quantity;
    }

    public Product subQuantity(Integer quantity) {
        if (!this.hasQuantity(quantity)) {
            throw new DomainException("Estoque insuficiente.");
        }

        this.quantity -= quantity;
        return this;
    }

    public Product addQuantity(Integer quantity) {
        this.quantity += quantity;
        return this;
    }

}
