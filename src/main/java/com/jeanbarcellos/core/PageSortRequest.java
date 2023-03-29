package com.jeanbarcellos.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.jeanbarcellos.project101.infra.configurations.constants.APIConstants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageSortRequest {

    // Sort string parse
    private static final String SORT_DELIMITER = ",";
    private static final String SORT_SEPARATOR = ":";

    // Sort Direction
    private static final String ASCENDING = "asc";
    private static final String DESCENDING = "desc";

    private Integer page = 1;
    private Integer size = 5;
    private String sort;

    public PageRequest toPageRequest() {
        return PageRequest.of(this.page - 1, this.size, createSort(this.sort));
    }

    public Sort toSort() {
        return createSort(this.sort);
    }

    // #region Builders

    public static PageSortRequest of(Integer page, Integer size) {
        return new PageSortRequest(page, size, null);
    }

    public static PageSortRequest of(Integer page, Integer size, String sort) {
        return new PageSortRequest(page, size, sort);
    }

    public static PageSortRequest of(Map<String, String> allParams) {
        return new PageSortRequest(extractPage(allParams), extractSize(allParams), extractSort(allParams));
    }

    // #endregion

    // #region Extract from Map

    private static Integer extractPage(Map<String, String> allParams) {
        var paramValue = allParams.get(APIConstants.PARAM_PAGE);
        return Integer.parseInt(
                ObjectUtils.isNotEmpty(paramValue) ? paramValue : APIConstants.PARAM_PAGE_DEFAULT);
    }

    private static Integer extractSize(Map<String, String> allParams) {
        var paramValue = allParams.get(APIConstants.PARAM_PAGE_SIZE);
        return Integer.parseInt(
                ObjectUtils.isNotEmpty(paramValue) ? paramValue : APIConstants.PARAM_PAGE_SIZE_DEFAULT);
    }

    private static String extractSort(Map<String, String> allParams) {
        var paramValue = allParams.get(APIConstants.PARAM_SORT);
        return ObjectUtils.isNotEmpty(paramValue) ? paramValue : APIConstants.PARAM_SORT_DEFAULT;
    }

    // #endregion

    // #region Sort

    private static Sort createSort(String fields) {
        var stringOrders = extractStringOrders(fields);

        var orders = stringOrders.stream()
                .map(PageSortRequest::createOrder)
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

    // #endregion

}
