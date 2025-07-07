package com.jeanbarcellos.core.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilitário para converter strings de ordenação em objetos {@link SortOrder}.
 * Suporta os seguintes formatos:
 * <ul>
 * <li>"campo:asc,campo2:desc"</li>
 * <li>"campo,asc,campo2,desc"</li>
 * <li>"+campo,-campo2"</li>
 * </ul>
 */
public class SortParser {

    private static final String TOKEN_SEPARATOR = ",";
    private static final String KEY_VALUE_SEPARATOR = ":";

    private SortParser() {
    }

    /**
     * Direção da ordenação.
     */
    public enum Direction {
        ASC("asc"),
        DESC("desc");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        /**
         * Converte string para direção.
         * Retorna ASC por padrão.
         *
         * @param input string representando a direção
         * @return {@link Direction}
         */
        public static Direction from(String input) {
            if (input == null) return ASC;
            return "desc".equalsIgnoreCase(input.trim()) ? DESC : ASC;
        }
    }

    /**
     * Representa uma ordenação por campo e direção.
     *
     * @param field     nome do campo
     * @param direction direção da ordenação
     */
    public record SortOrder(String field, Direction direction) {
    }

    /**
     * Faz o parse da lista de parâmetros de ordenação.
     *
     * @param sortParams lista de strings com os critérios
     * @return lista de {@link SortOrder}
     */
    public static List<SortOrder> parse(Collection<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Collections.emptyList();
        }

        return sortParams.stream()
                .flatMap(param -> parseSingle(param).stream())
                .collect(Collectors.toList());
    }

    private static List<SortOrder> parseSingle(String param) {
        List<SortOrder> result = new ArrayList<>();

        if (param.contains(KEY_VALUE_SEPARATOR)) {
            // Ex: name:asc,age:desc
            String[] pairs = param.split(TOKEN_SEPARATOR);
            for (String pair : pairs) {
                String[] parts = pair.trim().split(KEY_VALUE_SEPARATOR);
                if (parts.length >= 1) {
                    String field = parts[0].trim();
                    Direction direction = (parts.length > 1) ? Direction.from(parts[1]) : Direction.ASC;
                    result.add(new SortOrder(field, direction));
                }
            }
        } else if (param.contains(TOKEN_SEPARATOR)) {
            // Ex: name,asc,age,desc
            String[] parts = param.split(TOKEN_SEPARATOR);
            for (int i = 0; i < parts.length; i += 2) {
                String field = parts[i].trim();
                Direction direction = (i + 1 < parts.length) ? Direction.from(parts[i + 1]) : Direction.ASC;
                result.add(new SortOrder(field, direction));
            }
        } else {
            // Ex: -name,+age
            String[] tokens = param.split(TOKEN_SEPARATOR);
            for (String token : tokens) {
                token = token.trim();
                Direction direction = token.startsWith("-") ? Direction.DESC : Direction.ASC;
                String field = token.replaceFirst("^[+-]", "");
                result.add(new SortOrder(field, direction));
            }
        }

        return result;
    }

}