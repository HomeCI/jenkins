import hudson.model.BooleanParameterValue
import hudson.model.StringParameterValue

def call(Map params) {
    
    gitRepository = sh ( script: "echo $ref_repository_clone_url", returnStdout: true ).trim()
    branch = sh ( script: "echo $ref_ref", returnStdout: true ).trim().replaceFirst("refs/heads/", "")
    params['gitrepourl'] = gitRepository
    params['repobranch'] = branch
 
    def parameterValueList = params.collect { key, value ->
        if (value instanceof Boolean) {
            new BooleanParameterValue(key, value)
        } else {
            new StringParameterValue(key, value.toString())
        }
    }
    build job: 'DockerBuild', parameters: parameterValueList
}