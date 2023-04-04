library 'hci@main'

node(){
    stage('Clonando repositorio'){
        checkout([ $class: 'GitSCM', branches: [[name: 'main']], doGenerateSubmoduleConfigurations: false,
        extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'jenkins']],
        submoduleCfg: [], userRemoteConfigs: [[ url: 'https://github.com/alpeza/DummyWeb.git']]
        ])

        def pipelineScript = "Jenkinsfile"
        def scmVars = checkout(scm).getEnvVars()
        load(pipelineScript)
    }
    //docker tagname: "mypipeline:1.0.0", dbuild: false, pullToRegistry: false
}