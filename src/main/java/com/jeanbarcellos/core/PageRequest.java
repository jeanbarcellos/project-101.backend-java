package com.jeanbarcellos.core;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Objeto para Paginação e Ordenação de listas
 *
 * @author Jean Silva de Barcellos (www.jeanbarcellos.com.br)
 */
@ToString
@Setter
@Getter
@Accessors(chain = true)
public class PageRequest {

    /**
     * Pagina atual
     */
    private final Integer page;

    /**
     * Tamanho da página
     */
    private final Integer size;

    /**
     * Ordenação da página
     */
    private final String sort;

    private PageRequest(Integer page, Integer size, String sort) {
        Validate.notNull(page, "Argumento 'page' não pode ser nulo");
        Validate.notNull(size, "Argumento 'size' não pode ser nulo");

        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public Integer getIndex() {
        return this.page - 1;
    }

    public Integer getOffset() {
        return (this.getPage() - 1) * this.getSize();
    }

    public static PageRequest of(Integer page, Integer size) {
        return new PageRequest(page, size, null);
    }

    public static PageRequest of(Integer page, Integer size, String sort) {
        return new PageRequest(page, size, sort);
    }


}
