package com.jeanbarcellos.core.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.jeanbarcellos.core.PageRequest;

public class SpringPageAdapter {

    // Sort string parse
    private static final String SORT_SEPARATOR = ":";
    private static final String SORT_DELIMITER = ",";

    // Sort Direction
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    // Nao instanciavel
    private SpringPageAdapter(){
    }

    public static org.springframework.data.domain.PageRequest toPageRequest(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(
                pageRequest.getIndex(),
                pageRequest.getSize(),
                createSort(pageRequest.getSort()));
    }

    private static Sort createSort(String fields) {
        var stringOrders = extractStringOrders(fields);

        var orders = stringOrders.stream()
                .map(SpringPageAdapter::createOrder)
                .toList();

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
