def call(Map params) {
    String name = "core_npm_hcinet"
    if(params) name = params.name != null ? params.name : name
    print("Tratando de crear la red: ${name}")
    sh """
    docker network create --driver bridge --attachable ${name} || true
    """
}