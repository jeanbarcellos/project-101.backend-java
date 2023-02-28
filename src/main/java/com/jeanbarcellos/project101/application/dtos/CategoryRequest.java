package com.jeanbarcellos.project101.application.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "O campo 'name' não deve ser nulo ou estar vazio")
    @Size(min = 4, max = 128, message = "O campo 'name' deve possuir tamanho entre {min} e {max} caracteres")
    public String name;

}
