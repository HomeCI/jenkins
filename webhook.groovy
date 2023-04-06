pipelineJob('WebHook') {
    description('Pipeline template de ejemplo')
    triggers {
        GenericTrigger(
            genericVariables: [
                [key: 'ref', value: '$.ref']
            ],
            causeString: 'Triggered on $ref',
            token: 'abc123',
            printContributedVariables: true,
            printPostContent: true,
            silentResponse: false,
            regexpFilterText: '$ref',
            regexpFilterExpression: '^refs/heads/develop$'
        )
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
    }
}