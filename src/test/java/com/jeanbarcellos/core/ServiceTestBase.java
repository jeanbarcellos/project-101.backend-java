package com.jeanbarcellos.core;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;

import com.jeanbarcellos.core.exception.ValidationException;

/**
 * Abstraction for unit testing in Services
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
public abstract class ServiceTestBase {

protected static void assertExceptionMessageEquals(Exception exception, String message) {
        assertEquals(message, exception.getMessage());
    }

    protected static void assertExceptionMessageContains(Exception exception, String message) {
        assertThat(exception.getMessage(), CoreMatchers.containsString(message));
    }

    protected static void assertValidationExceptionMessageEquals(ValidationException exception, String message) {
        assertEquals(message, exception.getMessage());
        // assertThat(exception.getMessage(), CoreMatchers.is(message));
    }

    protected static void assertValidationExceptionMessageContains(ValidationException exception, String message) {
        assertThat(exception.getMessage(), CoreMatchers.containsString(message));
    }

    protected static void assertValidationExceptionErrorListSize(ValidationException exception, Integer size) {
        assertEquals(size, exception.getErrors().size());
    }

    protected static void assertValidationExceptionErrorListHasItem(ValidationException exception, String message) {
        assertThat(exception.getErrors(), CoreMatchers.hasItem(message));
    }

    protected static void assertValidationExceptionErrorListHasItems(ValidationException exception, String... message) {
        assertThat(exception.getErrors(), CoreMatchers.hasItems(message));
    }

    protected static void assertValidationExceptionErrorListContains(ValidationException exception, String value) {
        var condition = containsInCollection(exception.getErrors(), getPredicateContains(value));

        assertTrue(condition, String.format("The list does not contain the value: %s", value));
    }

    protected static void assertValidationExceptionErrorListContains(ValidationException exception, String... values) {
        var vals = Arrays.asList(values);

        List<String> notList = new ArrayList<>();
        for (String value : vals) {
            if (!containsInCollection(exception.getErrors(), getPredicateContains(value))) {
                notList.add(value);
            }
        }

        assertTrue(notList.isEmpty(), String.format("The list does not contain the values: %s", notList.toString()));
    }

    private static Predicate<String> getPredicateContains(String value) {
        return error -> StringUtils.contains(error, value);
    }

    private static <T extends Object> boolean containsInCollection(Collection<T> collection, Predicate<? super T> predicate) {

        if (collection == null) {
            collection = Collections.emptyList();
        }

        if (predicate == null) {
            return false;
        }

        Optional<T> resultOp = collection
                .stream()
                .parallel()
                .filter(predicate)
                .findAny();

        return resultOp.isPresent();
    }
}
