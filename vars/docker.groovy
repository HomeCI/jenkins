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
         int errorCount = 0
         
        println(params)
        // Validar el patrón de etiqueta de imagen de Docker
        // Validar el patrón de etiqueta de imagen de Docker
        if (!tagname.matches("^[a-zA-Z0-9_][a-zA-Z0-9_.-]{0,127}(:[a-zA-Z0-9_.-]{1,32})?\$")) {
            error("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            errorCount++
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && params.username == null) {
            error("Se requiere el nombre de usuario para hacer pull de la imagen del registro de contenedores.")
            errorCount++
        }

        def dockerfile = new File(dockerfilePath, "Dockerfile")
        if (dbuild && !dockerfile.exists()) {
            error("El archivo Dockerfile no se encuentra en la ruta especificada.")
            errorCount++
        }

        def dockercompose = new File(dockercomposePath, "docker-compose.yml")
        if (deploy && !dockercompose.exists()) {
            error("El archivo docker-compose.yml no se encuentra en la ruta especificada.")
            errorCount++
        }

        if (pullToRegistry && !tagname.matches("^[a-zA-Z0-9]+(/[a-zA-Z0-9]+)*(:[a-zA-Z0-9.-]+)?\\$")) {
            error("El nombre de etiqueta de DockerHub no cumple con la sintaxis de etiqueta de imagen de Docker.")
            errorCount++
        }

        if (errorCount > 0) {
            asciiBox("Revise los errores detectados")
            sh "exit $errorCount"
        }

        asciiBox("Validación correcta")
    }

    stage('Building'){
       println("Construyendo la imagen ...")
       sh """
       set -x 
       cd ${dockerfilePath}
       docker build -t ${tagname} .
       """
    }

    stage('Deploying'){
        println("Desplegando el compose ... ")
        sh """
        set -x 
        cd ${dockercomposePath}
        docker-compose up -d
        """
    }

    stage('Promoting'){
        println("Promocionando imagen ... ")
        sh """
        set -x 
        docker pull
        """
    }


}