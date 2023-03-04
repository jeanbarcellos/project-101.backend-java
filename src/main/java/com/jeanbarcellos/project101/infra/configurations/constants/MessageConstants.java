package com.jeanbarcellos.project101.infra.configurations.constants;

/**
 * Mensagens gerais do serviço
 */
public class MessageConstants {

    private MessageConstants() {

    }

    /*
     * Mensagens de erro padrão
     */

    // StatusCode 500
    public static final String ERROR_SERVICE = "Erro inesperado no serviço. Se o problema persistir entre em contato com o administrador.";

    // StatusCode 400
    public static final String ERROR_REQUEST = "A requisição falhou devido a erros nos dados fornecidos.";

    // StatusCode 404
    public static final String ERROR_NOT_FOUND = "Recurso não encontrado.";

    // StatusCode 400 exlusico para valicacao
    public static final String ERROR_VALIDATION = "Os dados informados estão inválidos.";

}
