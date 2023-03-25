package com.jeanbarcellos.project101.application.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductRequest {

    @JsonIgnore
    private UUID id;

    @NotNull
    private UUID categoryId;

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String image;

    @NotNull
    private Boolean active;

    @NotNull
    private BigDecimal value;

    @NotNull
    @Range(min = 1, max = Integer.MAX_VALUE)
    private Integer quantity;

}
