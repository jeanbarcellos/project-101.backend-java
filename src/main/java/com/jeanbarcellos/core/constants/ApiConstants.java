package com.jeanbarcellos.core.constants;

/**
 * API Constants
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 *
 */
public class ApiConstants {

    /**
     * Auth
     */

    public static final String BEARER_KEY = "bearer-key";

    public static final String HTTP_AUTH_SCHEME_BEARER = "bearer";
    public static final String HTTP_AUTH_SCHEME_BEARER_FORMAT = "JWT";

    /**
     * Status
     */

    public static final String STATUS_200_DESCRIPTION = "Sucesso no processamento da requisição.";
    public static final String STATUS_201_DESCRIPTION = "Recurso criado com sucesso.";
    public static final String STATUS_204_DESCRIPTION = "Requisição recebida com sucesso porém não há conteúdo a exibir.";
    public static final String STATUS_400_DESCRIPTION = "Requisição inválida.";
    public static final String STATUS_401_DESCRIPTION = "Acesso não autorizado.";
    public static final String STATUS_403_DESCRIPTION = "Acesso não permitido por falta de permissões.";
    public static final String STATUS_404_DESCRIPTION = "Recurso solicitado não encontrado.";
    public static final String STATUS_500_DESCRIPTION = "Erro inesperado no sistema.";

    /**
     * Content
     */
    public static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    /*
     * Paginação
     */

    // Valores padrão para os parâmetros de paginação
    public static final Integer PAGE_CURRENT_DEFAULT = 1;
    public static final String  PAGE_CURRENT_DEFAULT_STRING = "1";
    public static final Integer PAGE_SIZE_DEFAULT = 20;
    public static final String  PAGE_SIZE_DEFAULT_STRING = "20";

    // Parâmetros padrão para paginação
    public static final String PARAM_PAGE_CURRENT = "page";
    public static final String PARAM_PAGE_SIZE = "size";
    public static final String PARAM_SORT = "sort";
    // Parâmetros para ordenação
    public static final String PARAM_SORT_DEFAULT = "";
    public static final String PARAM_SORT_ID_DESC = "id:desc";
    public static final String PARAM_SORT_CREATED_DESC = "createdAt:desc";

    // Headers padrão para resposta de paginação
    public static final String PAGINATION_PAGE_CURRENT_KEY = "x-pagination-page-current";
    public static final String PAGINATION_PAGE_SIZE_KEY    = "x-pagination-page-size"; // ou limit
    public static final String PAGINATION_TOTAL_PAGES_KEY  = "x-pagination-total-pages";
    public static final String PAGINATION_TOTAL_COUNT_KEY  = "x-pagination-total-count";
    //
    public static final String PAGINATION_PAGE_CURRENT_DESCRIPTION = "Página atual";
    public static final String PAGINATION_PAGE_SIZE_DESCRIPTION    = "Registros por página";
    public static final String PAGINATION_TOTAL_PAGES_DESCRIPTION  = "Número total de páginas";
    public static final String PAGINATION_TOTAL_COUNT_DESCRIPTION  = "Número total de registros";
    //
    public static final String PAGINATION_HEADER_SCHEMA = "string";

    private ApiConstants() {
    }

}
