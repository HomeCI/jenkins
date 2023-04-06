library 'hci@main'

node(){
    stage('Clonando repositorio'){
        sh "echo $ref"
       
        sh "echo '----------------'"
        sh "env"
        sh '''

        for i in _ {a..z} {A..Z}; do
   for var in `eval echo "\\${!$i@}"`; do
      echo $var
      # you can test if $var matches some criteria and put it in the file or ignore
   done 
done
        '''
    }
}