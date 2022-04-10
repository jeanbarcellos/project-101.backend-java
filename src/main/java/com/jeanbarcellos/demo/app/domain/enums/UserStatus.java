package com.jeanbarcellos.demo.app.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus {
    INACTIVE(0),
    ACTIVE(1);

    public final Integer label;

    private UserStatus(Integer label) {
        this.label = label;
    }

    @JsonValue
    public Integer toValue() {
        return label;
    }
}
