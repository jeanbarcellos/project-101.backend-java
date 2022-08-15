package com.jeanbarcellos.demo.application.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull(message = "Valor não informado")
    @NotBlank
    @Size(min = 4, max = 128, message = "O tamanho deve ser entre 4 e 128 caracteres")
    public String name;

}
