import groovy.json.*
import groovyx.net.http.HttpBuilder

/**
 * Crea un nuevo proveedor de tipo proxy con los parámetros proporcionados.
 *
 * @param parametros Un mapa que contiene los parámetros para crear el proveedor.
 *                   Debe incluir las claves "internal_host", "external_host" y "name".
 *                   "internal_host" y "external_host" deben seguir el patrón de http o https seguido de un nombre de dominio válido.
 * @throws IllegalArgumentException Si "internal_host" o "external_host" no siguen el patrón de http o https seguido de un nombre de dominio válido.
 */
def createProvider(Map parametros) {
    def internalHost = parametros.internal_host
    def externalHost = parametros.external_host
    def name = parametros.name

    // Validar que los valores sigan el patrón de http o https seguido de un nombre de dominio válido
    def validacion = ~/^(https?):\/\/([a-z0-9]+(-[a-z0-9]+)*\.)+[a-z]{2,}$/i
    if (!validacion.matcher(internalHost).matches() || !validacion.matcher(externalHost).matches()) {
        throw new IllegalArgumentException("Los valores para internal_host y external_host deben seguir el patrón de http o https seguido de un nombre de dominio válido.")
    }

    def json = [
        name: name,
        authorization_flow: "c789e652-d690-4782-9bea-aae9685a7efb",
        internal_host: internalHost,
        external_host: externalHost,
        internal_host_ssl_validation: false,
        skip_path_regex: "string",
        basic_auth_enabled: false,
        basic_auth_password_attribute: "string",
        basic_auth_user_attribute: "string",
        mode: "proxy",
        intercept_header_auth: false,
        cookie_domain: "string"
    ]
    def jsonString = JsonOutput.toJson(json)

    def http = new HttpBuilder("http://core_authentik-server-1:9090/api/v3/providers/proxy/")

    // Establecer las cabeceras
    http.request(POST, JSON) {
        headers.accept = "application/json"
        headers."content-type" = "application/json"
        body = jsonString
        response.success = { resp, json ->
            println "La solicitud fue exitosa"
            println "Respuesta: $resp.statusLine"
            println "Cuerpo de respuesta: $json"
        }
        response.failure = { resp ->
            println "La solicitud falló"
            println "Respuesta: $resp.statusLine"
            println "Cuerpo de respuesta: $resp.entity.content.text"
        }
    }
}
