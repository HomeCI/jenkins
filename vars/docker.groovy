def call(Map params) {
    def job = Jenkins.instance.getItemByFullName('DockerBuild')
    def paramAction = new hudson.model.ParametersAction(params.collect { param -> new hudson.model.StringParameterValue(param.key, param.value) })
    job.build(paramAction)
}