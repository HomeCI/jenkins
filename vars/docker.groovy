def call(Map params) {

    String tagname = params.tagname 
    boolean deploy = params.deploy ?: true
    boolean build = params.build ?: true
    String dockerfilePath = params.dockerfilePath ?: "."
    String username = params.username
    boolean pullToRegistry = params.pullToRegistry ?: false
    
    /** Input */
    stage('Validating input'){
        info(params)
        // Validar el patrón de etiqueta de imagen de Docker
        if (!tagname.matches("^[a-zA-Z0-9_][a-zA-Z0-9_.-]{0,127}(:[a-zA-Z0-9_.-]{1,32})?\$")) {
            error("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            sh 'exit 1'
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && username == null) {
            error("Se requiere el nombre de usuario para hacer pull de la imagen del registro de contenedores.")
            sh 'exit 1'
        }

        def dockerfile = new File(dockerfilePath, "Dockerfile")
        if (build && !dockerfile.exists()) {
            error("El archivo Dockerfile no se encuentra en la ruta especificada.")
            sh 'exit 1'
        }

        info("Validación correcta")
    }

    stage('Building'){
       info("Building")
    }

    stage('Deploying'){
        info("Deploying")
    }


    // Lógica adicional de la función aquí
}