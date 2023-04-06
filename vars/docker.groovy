def call(Map params) {
    build job: 'DockerBuild', parameters: params
}