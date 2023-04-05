def call(Map params) {
    String name = params.tagname != null ? params.name : "core_npm_hcinet"
    print("Tratando de crear la red: ${name}")
    sh """
    docker network create --driver bridge --attachable ${name} || true
    """
}