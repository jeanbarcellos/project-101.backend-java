package com.jeanbarcellos.demo.config;

public class Roles {

    private Roles() {

    }

    private static final String HAS_HOLE_START = "hasRole('";
    private static final String HAS_HOLE_END = "')";

    // Somente desenvolvedor ou dono do software
    public static final String ROOT = "root";

    // Geral
    public static final String ADMIN = "admin";
    public static final String DEFAULT = "default";

    //
    public static final String HAS_ROLE_ROOT = HAS_HOLE_START + ROOT + HAS_HOLE_END;
    public static final String HAS_ROLE_ADMIN = HAS_HOLE_START + ADMIN + HAS_HOLE_END;
    public static final String HAS_ROLE_DEFAULT = HAS_HOLE_START + DEFAULT + HAS_HOLE_END;
}
