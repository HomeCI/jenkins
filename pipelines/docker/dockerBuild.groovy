library 'hci@main'

node(){
    stage('Checkout'){
        checkout([
            $class: 'GitSCM',
            branches: [[name: 'main']],
            userRemoteConfigs: [[url: 'https://github.com/alpeza/DummyWeb.git']],
            extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'repo']]
        ])
    }
    dir('repo'){
        dockerCore tagname: params.tagname,
                   deploy: params.deploy,
                   build: params.dbuild,
                   dockerfilePath: params.dockerfilePath,
                   dockercomposePath: params.dockercomposePath,
                   credentialsId: params.credentialsId,
                   pullToRegistry: params.pullToRegistry
    }
}