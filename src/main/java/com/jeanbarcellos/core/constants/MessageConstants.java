package com.jeanbarcellos.core.constants;

/**
 * Mensagens gerais do serviço
 *
 * @author Jean Silva de Barcellos (jeanbarcellos@hotmail.com)
 */
public class MessageConstants {

    /*
     * Mensagens de erro padrão
     */

    public static final String MSG_ERROR_SERVICE = "Erro inesperado no serviço. Se o problema persistir entre em contato com o administrador.";
    public static final String MSG_ERROR_REQUEST = "A requisição falhou devido a erros nos dados fornecidos.";
    public static final String MSG_ERROR_NOT_FOUND = "Recurso não encontrado.";
    public static final String MSG_ERROR_FORBIDDEN = "Acesso não autorizado.";

    public static final String MSG_ERROR_VALIDATION = "Os dados informados estão inválidos.";
    public static final String MSG_ERROR_VALIDATION_FIELD_LIST = "O campo '%s' %s";

    public static final String MGG_ERROR_VALIDATION_JSON_MALFORMATED = "Malformed request or invalid data format."; // Mapping error. Malformed JSON or type mismatch.
    public static final String MGG_ERROR_VALIDATION_JSON_INVALID_FORMAT = "has invalid type or format";

    public static final String MSG_ERROR_ENTITY_NOT_FOUND = "There is no %s with the given ID '%s'.";
    public static final String MSG_ERROR_OPTIMISTIC_LOCKING = "The current state of the entity changed before you completed the operation. Please try again.";

    public static final String MSG_ERROR_REQUEST_IDEMPOTENT = "A request with ID (requestId) '%s' has already been submitted.";

    public static final String MSG_ERROR_HTTP_METHOD_NOT_SUPPORTED = "HTTP method not supported: %s";

    // not instantiable
    private MessageConstants() {
    }

}