library 'hci@main'

node(){
    stage('Checking'){
        repourl = sh ( script: "echo $ref_repository_clone_url",returnStdout: true).trim()
        println("Repositorio: ${repourl}" )
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            userRemoteConfigs: [[url: repourl]],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'repo']]
        ])
        dir('repo'){
            load('Jenkinsfile')
        }
    }
}