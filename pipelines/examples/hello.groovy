library 'hci@main'

node(){
    stage('Clonando repositorio'){
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            doGenerateSubmoduleConfigurations: false,
            userRemoteConfigs: [[url: 'https://github.com/alpeza/DummyWeb.git']],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'repo']]
        ])
        sh "ls -a"
        dir('repo'){
            load('Jenkinsfile')
        }
    }
    //docker tagname: "mypipeline:1.0.0", dbuild: false, pullToRegistry: false
}