library 'hci@main'

node(){
    stage('Clonando repositorio'){
        sh "echo $repository"
        sh "echo '----------------'"
        sh "env"
    }
}