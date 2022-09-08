package com.jeanbarcellos.demo.config;

public class Roles {

    private Roles() {

    }

    // Somente desenvolvedor ou dono do software
    public static final String ROOT = "root";

    // Geral
    public static final String ADMIN = "admin";
    public static final String DEFAULT = "default";

    //
    public static final String HAS_ROLE_ROOT = "hasRole('" + ROOT + "')";
    public static final String HAS_ROLE_ADMIN = "hasRole('" + ADMIN + "')";
    public static final String HAS_ROLE_DEFAULT = "hasRole('" + DEFAULT + "')";
}
