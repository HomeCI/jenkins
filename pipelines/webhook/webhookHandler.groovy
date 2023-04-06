library 'hci@main'

node(){
    stage('Clonando repositorio'){
        //sh "echo $ref"
        println("Aqui")
        gitCommit = sh ( script: "echo $ref",returnStdout: true).trim()
        def gitCommitParsed = new JsonSlurper().parseText(gitCommit)
        println(gitCommitParsed)

    }
}