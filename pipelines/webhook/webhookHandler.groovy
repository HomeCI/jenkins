library 'hci@main'

node(){
    stage('Clonando repositorio'){
        sh "echo $ref"
        sh "echo '----------------'"
        print(env)
    }
}