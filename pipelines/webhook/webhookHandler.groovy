library 'hci@main'
import groovy.json.JsonSlurper
node(){
    stage('Clonando repositorio'){
        //sh "echo $ref"
        println("Aqui")
        repo = sh ( script: "echo $ref_repository_git_url",returnStdout: true).trim()
        println("Repositorio: ${repo}" )

    }
}