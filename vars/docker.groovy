import hudson.model.StringParameterValue

def call(Map params) {
    def parameterValueList = params.collect { key, value ->
        new StringParameterValue(key, value)
    }
    build job: 'DockerBuild', parameters: parameterValueList
}