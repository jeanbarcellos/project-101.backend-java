package com.jeanbarcellos.core.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * DTO para Paginação e Ordenação de listas
 *
 * @author Jean Silva de Barcellos (www.jeanbarcellos.com.br)
 */
@ToString
@Setter
@Getter
@Accessors(chain = true)
public class PageRequest {

    // Sort string parse
    private static final String SORT_SEPARATOR = ":";
    private static final String SORT_DELIMITER = ",";

    // Sort Direction
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

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

    public org.springframework.data.domain.PageRequest toPageRequest() {
        return org.springframework.data.domain.PageRequest.of(this.page - 1, this.size, createSort(this.sort));
    }

    public Sort toSort() {
        return createSort(this.sort);
    }

    public static PageRequest of(Integer page, Integer size) {
        return new PageRequest(page, size, null);
    }

    public static PageRequest of(Integer page, Integer size, String sort) {
        return new PageRequest(page, size, sort);
    }

    private static Sort createSort(String fields) {
        var stringOrders = extractStringOrders(fields);

        var orders = stringOrders.stream()
                .map(PageRequest::createOrder)
                .collect(Collectors.toList());

        return Sort.by(orders);
    }

    private static List<String> extractStringOrders(String orders) {
        List<String> parameters = new ArrayList<>();

        if (orders.isEmpty()) {
            return parameters;
        }

        Collections.addAll(parameters, orders.split(SORT_DELIMITER));

        return parameters;
    }

    private static Order createOrder(String nameAndDirection) {
        String[] parts = splitDirection(nameAndDirection);

        Direction direction = parts[1].equals(ASCENDING)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return new Order(direction, parts[0]);
    }

    private static String[] splitDirection(String nameAndDirection) {
        String[] parts = nameAndDirection.split(SORT_SEPARATOR);
        String name = parts[0];
        String dir = ASCENDING;

        if (parts.length > 1) {
            validateDirection(parts[1]);
            dir = parts[1].toLowerCase();
        }

        return new String[] { name, dir };
    }

    private static void validateDirection(String direction) {
        if (!Arrays.asList(ASCENDING, DESCENDING).contains(direction.toLowerCase())) {
            throw new IllegalArgumentException(
                    String.format("A direção deve ser '%s' ou '%s'.", ASCENDING, ASCENDING));
        }
    }

}
