def call(Map params) {
    LinkedHashMap<T> newMap = new LinkedHashMap<T>(params);
    build job: 'DockerBuild', parameters: newMap
}