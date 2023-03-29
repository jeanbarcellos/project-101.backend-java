package com.jeanbarcellos.project101.infra.configurations.constants;

public class APIConstants {

    private APIConstants() {
    }

    /**
     * Auth
     */

    public static final String BEARER_KEY = "bearer-key";

    public static final String HTTP_AUTH_SCHEME_BEARER = "bearer";
    public static final String HTTP_AUTH_SCHEME_BEARER_FORMAT = "JWT";

    /**
     * Status
     */

    public static final String STATUS_200_DESCRIPTION = "Detalhes do recurso.";
    public static final String STATUS_201_DESCRIPTION = "Criado com sucesso";
    public static final String STATUS_400_DESCRIPTION = "Requisição inválida.";
    public static final String STATUS_401_DESCRIPTION = "Acesso não autorizado.";
    public static final String STATUS_403_DESCRIPTION = "Acesso não permitido.";
    public static final String STATUS_404_DESCRIPTION = "Recurso não encontrada a partir do documento informado.";
    public static final String STATUS_500_DESCRIPTION = "Erro inesperado no sistema.";

    /**
     * Content
     */
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    /*
     * Paginação
     */

    // Parâmetros padrão para paginação
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_PAGE_SIZE = "size";
    public static final String PARAM_SORT = "sort";

    // Valores padrão para os parâmetros de paginação
    public static final String PARAM_PAGE_DEFAULT = "1";
    public static final String PARAM_PAGE_SIZE_DEFAULT = "20";
    public static final String PARAM_SORT_DEFAULT = "";
    public static final String PARAM_SORT_CREATED_DESC = "createdAt:desc";
    public static final String PARAM_SORT_ID_DESC = "createdAt:desc";

    // Headers padrão para resposta de paginação
    public static final String PAGINATION_KEY_CURRENT_PAGE = "pagination-page";
    public static final String PAGINATION_KEY_PER_PAGE = "pagination-per-page";
    public static final String PAGINATION_KEY_PAGES = "pagination-pages";
    public static final String PAGINATION_KEY_TOTAL = "pagination-total";
    public static final String PAGINATION_DESCRIPTION_CURRENT_PAGE = "Página atual";
    public static final String PAGINATION_DESCRIPTION_PER_PAGE = "Registros por página";
    public static final String PAGINATION_DESCRIPTION_PAGES = "Número total de páginas";
    public static final String PAGINATION_DESCRIPTION_TOTAL = "Número total de registros";
    public static final String PAGINATION_HEADER_SCHEMA = "string";

}
