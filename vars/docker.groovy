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
        if (!tagname.matches("^(?:(?=[^:\/]{1,253})(?!-)[a-zA-Z0-9-]{1,63}(?<!-)(?:\.(?!-)[a-zA-Z0-9-]{1,63}(?<!-))*(?::[0-9]{1,5})?/)?((?![._-])(?:[a-z0-9._-]*)(?<![._-])(?:/(?![._-])[a-z0-9._-]*(?<![._-]))*)(?::(?![.-])[a-zA-Z0-9_.-]{1,128})?\$")) {
            println("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            errorCount++
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && params.username == null) {
            println("Se requiere el nombre de usuario para hacer pull de la imagen del registro de contenedores.")
            errorCount++
        }

        def dockerfile = new File(dockerfilePath, "Dockerfile")
        print(dockerfile)
        if (dbuild && !dockerfile.exists()) {
            println("El archivo Dockerfile no se encuentra en la ruta especificada.")
            errorCount++
        }

        def dockercompose = new File(dockercomposePath, "docker-compose.yml")
        print(dockercompose)
        if (deploy && !dockercompose.exists()) {
            println("El archivo docker-compose.yml no se encuentra en la ruta especificada.")
            errorCount++
        }

        if (pullToRegistry && !tagname.matches("^(?:(?=[^:\/]{1,253})(?!-)[a-zA-Z0-9-]{1,63}(?<!-)(?:\.(?!-)[a-zA-Z0-9-]{1,63}(?<!-))*(?::[0-9]{1,5})?/)?((?![._-])(?:[a-z0-9._-]*)(?<![._-])(?:/(?![._-])[a-z0-9._-]*(?<![._-]))*)(?::(?![.-])[a-zA-Z0-9_.-]{1,128})?\$")) {
            println("El nombre de etiqueta de DockerHub no cumple con la sintaxis de etiqueta de imagen de Docker.")
            errorCount++
        }

        if (errorCount > 0) {
            println("Revise los errores detectados")
            //sh "exit $errorCount"
        }

        println("Validación correcta")
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
        docker compose up -d
        """
    }

    stage('Promoting'){
        println("Promocionando imagen ... ")
        withCredentials([usernamePassword(credentialsId: username, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
            sh """
            set -x;
            docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"
            docker pull ${tagname}
            """
        }
    }

}