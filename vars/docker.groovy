import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

def call(String tagname, String username = null, boolean pullToRegistry = false) {
    // Crear un objeto Logger
    final Logger logger = LoggerFactory.getLogger("Docker");


    stage('Validating input'){
        // Validar el patrón de etiqueta de imagen de Docker
        if (!tagname.matches("^[a-zA-Z0-9_][a-zA-Z0-9_.-]{0,127}(:[a-zA-Z0-9_.-]{1,32})?\$")) {
            logger.error("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            sh 'exit 1'
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && username == null) {
            logger.error("Se requiere el nombre de usuario para hacer pull de la imagen del registro de contenedores.")
            sh 'exit 1'
        }

        logger.info("Validación correcta")
    }

    stage('Building'){
       logger.info("Building")
    }

    stage('Deploying'){
        logger.info("Deploying")
    }


    // Lógica adicional de la función aquí
}