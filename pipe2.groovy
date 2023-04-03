pipelineJob('HelloExample') {

    definition {
        cps {
            script('''
                node {
                    load "/home/pipe2/Jenkinsfile"
                }
            ''')
        }
    }
}