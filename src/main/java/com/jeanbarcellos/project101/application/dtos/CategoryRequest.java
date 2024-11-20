package com.jeanbarcellos.project101.application.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CategoryRequest {

    @JsonIgnore
    private UUID id;

    @NotBlank
    @Size(min = 4, max = 128)
    private String name;

}
