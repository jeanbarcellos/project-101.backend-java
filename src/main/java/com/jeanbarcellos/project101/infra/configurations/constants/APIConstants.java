package com.jeanbarcellos.project101.infra.configurations.constants;

public class APIConstants {

    private APIConstants() {
    }

    public static final String BEARER_KEY = "bearer-key";

    public static final String HTTP_AUTH_SCHEME_BEARER = "bearer";
    public static final String HTTP_AUTH_SCHEME_BEARER_FORMAT = "JWT";

    public static final String STATUS_200_DESCRIPTION = "Detalhes do recurso.";
    public static final String STATUS_201_DESCRIPTION = "Criado com sucesso";
    public static final String STATUS_400_DESCRIPTION = "Requisição inválida.";
    public static final String STATUS_401_DESCRIPTION = "Acesso não autorizado.";
    public static final String STATUS_403_DESCRIPTION = "Acesso não permitido.";
    public static final String STATUS_404_DESCRIPTION = "Recurso não encontrada a partir do documento informado.";
    public static final String STATUS_500_DESCRIPTION = "Erro inesperado no sistema.";

    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

}
