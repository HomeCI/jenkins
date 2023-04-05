def call(Map params) {
    
    params.deploy  = params.deploy != null ? params.deploy : true
    params.dbuild = params.dbuild != null ? params.dbuild : true
    params.dockerfilePath = params.dockerfilePath != null ? params.dockerfilePath : "."
    params.dockercomposePath = params.dockercomposePath != null ? params.dockercomposePath : "."
    params.pullToRegistry = params.pullToRegistry != null ? params.pullToRegistry : false

    def job = Jenkins.instance.getItemByFullName('DockerBuild')
    def paramAction = new hudson.model.ParametersAction(params.collect { param -> new hudson.model.StringParameterValue(param.key, param.value) })
    job.build(paramAction)
}