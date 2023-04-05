library 'hci@main'

node(){
    stage('Clonando repositorio'){
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            userRemoteConfigs: [[url: 'https://github.com/alpeza/DummyWeb.git']],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'repo']]
        ])
        dir('repo'){
            load('Jenkinsfile')
        }
    }
}