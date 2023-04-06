import hudson.model.BooleanParameterValue
import hudson.model.StringParameterValue

def call(Map params) {
    def parameterValueList = params.collect { key, value ->
        if (value instanceof Boolean) {
            new BooleanParameterValue(key, value)
        } else {
            new StringParameterValue(key, value.toString())
        }
    }
    build job: 'DockerBuild', parameters: parameterValueList
}