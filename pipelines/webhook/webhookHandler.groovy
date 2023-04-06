library 'hci@main'

node(){
    stage('Clonando repositorio'){
        sh "echo $ref"
        sh "cat refs/heads/main"
        sh "echo '----------------'"
        sh "env"
    }
}