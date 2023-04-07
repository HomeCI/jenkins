def call(Map params) {

    String tagname = params.tagname 
    boolean deploy = params.deploy != null ? params.deploy : true
    boolean dbuild = params.dbuild != null ? params.dbuild : true
    String dockerfilePath = params.dockerfilePath != null ? params.dockerfilePath : "."
    String dockercomposePath = params.dockercomposePath != null ? params.dockercomposePath : "."
    String credentialsId = params.credentialsId
    boolean pullToRegistry = params.pullToRegistry != null ? params.pullToRegistry : false
    
    /** Input */
    stage('Validating input'){
        int errorCount = 0
        println(params)
        // Validar el patrón de etiqueta de imagen de Docker
        if (!tagname.matches("^(?:(?=[^:/]{1,253})(?!-)[a-zA-Z0-9-]{1,63}(?<!-)(?:.(?!-)[a-zA-Z0-9-]{1,63}(?<!-))*(?::[0-9]{1,5})?/)?((?![._-])(?:[a-z0-9._-]*)(?<![._-])(?:/(?![._-])[a-z0-9._-]*(?<![._-]))*)(?::(?![.-])[a-zA-Z0-9_.-]{1,128})?\$")) {
            println("La etiqueta de imagen no sigue el patrón de imagen de Docker.")
            errorCount++
        }

        // Validar si pullToRegistry está marcado y si se proporcionó un nombre de usuario
        if (pullToRegistry && params.credentialsId == null) {
            println("Se requiere de un credentialsId para hacer push de la imagen del registro de contenedores.")
            errorCount++
        }


        if (dbuild && utils.fileExists(dockerfilePath,'Dockerfile')) {
            println("El archivo Dockerfile no se encuentra en la ruta especificada.")
            errorCount++
        }

        if (deploy && ( utils.fileExists(dockercomposePath,'docker-compose.yaml') || utils.fileExists(dockercomposePath,'docker-compose.yml'))) {
            println("El archivo docker-compose.yml no se encuentra en la ruta especificada.")
            errorCount++
        }


        if (errorCount > 0) {
            asciiBox("Revise los errores detectados")
            sh "exit $errorCount"
        }

        println("Validación correcta")
    }

    stage('Building'){
       asciiBox("Construyendo la imagen ...")
       sh """
       set -x 
       cd ${dockerfilePath}
       docker build -t ${tagname} .
       """
    }

    stage('Deploying'){
        asciiBox("Desplegando el compose ... ")
        sh """
        set -x 
        cd ${dockercomposePath}
        docker compose down --remove-orphans
        docker compose up -d
        """
    }

    stage('Promoting'){
        asciiBox("Promocionando imagen ... ")
        withCredentials([usernamePassword(credentialsId: credentialsId, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
            sh """
            set -x;
            docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD
            docker push ${tagname}
            """
        }
    }

}