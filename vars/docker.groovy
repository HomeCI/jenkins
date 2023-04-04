def call(Map params) {

    String tagname = params.tagname 
    boolean deploy = params.deploy != null ? params.deploy : true
    boolean dbuild = params.dbuild != null ? params.dbuild : true
    String dockerfilePath = params.dockerfilePath != null ? params.dockerfilePath : "."
    String dockercomposePath = params.dockercomposePath != null ? params.dockercomposePath : "."
    String username = params.username
    boolean pullToRegistry = params.pullToRegistry != null ? params.pullToRegistry : false
    
    /** Input */
    stage('Validating input'){

        println(params)
        // Validar el patrón de etiqueta de imagen de Docker
        if (!tagname.matches("^[a-zA-Z0-9_][a-zA-Z0-9_.-]{0,127}(:[a-zA-Z0-9_.-]{1,32})?\$")) {
            error("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            sh 'exit 1'
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && params.username == null) {
            error("Se requiere el nombre de usuario para hacer pull de la imagen del registro de contenedores.")
            sh 'exit 1'
        }

        def dockerfile = new File(dockerfilePath, "Dockerfile")
        if (dbuild && !dockerfile.exists()) {
            error("El archivo Dockerfile no se encuentra en la ruta especificada.")
            sh 'exit 1'
        }

        def dockercompose = new File(dockercomposePath, "docker-compose.yml")
        if (deploy && !dockercompose.exists()) {
            error("El archivo docker-compose.yml no se encuentra en la ruta especificada.")
            sh 'exit 1'
        }

        println("Validación correcta")
    }

    stage('Building'){
       println("Building")
       sh """
       set -x 
       cd ${dockerfilePath}
       docker build -t ${tagname} .
       """
    }

    stage('Deploying'){
        println("Deploying")
    }


    // Lógica adicional de la función aquí
}