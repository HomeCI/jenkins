def call(Map params) {
    def linkedHashMapParams = params as LinkedHashMap
    build job: 'DockerBuild', parameters: linkedHashMapParams
}