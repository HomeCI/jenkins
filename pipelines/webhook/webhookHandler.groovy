library 'hci@main'

node(){
    stage('Clonando repositorio'){
        sh "echo $ref"
        sh "echo '----------------'"
        sh "env"
    }
}