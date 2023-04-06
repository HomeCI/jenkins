pipelineJob('WebHook') {
    description('Pipeline template de ejemplo')
    triggers {
        genericTrigger {
            genericVariables {
                genericVariable {
                key("ref")
                value("\$.ref")
                //expressionType("JSONPath") //Optional, defaults to JSONPath
                //regexpFilter("") //Optional, defaults to empty string
                //defaultValue("") //Optional, defaults to empty string
                }
            }
            genericRequestVariables {
                genericRequestVariable {
                key("requestParameterName")
                regexpFilter("")
                }
            }
            genericHeaderVariables {
                genericHeaderVariable {
                key("requestHeaderName")
                regexpFilter("")
                }
            }
            token('abc123')
            tokenCredentialId('')
            printContributedVariables(true)
            printPostContent(true)
            silentResponse(false)
            shouldNotFlattern(false)
            regexpFilterText("\$ref")
            regexpFilterExpression(".*")
        }
    }
    definition {
        configure { root ->
            def paramDefs = root / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions'

            paramDefs << 'com.seitenbau.jenkins.plugins.dynamicparameter.StringParameterDefinition' {
                delegate.createNode('name', 'BRANCHSPEC')
                delegate.createNode('__script', 'def getversion= ["/bin/bash", "-c", "cd /var/jenkins_home/version;cat version.txt"].execute().text;def version=getversion.readLines();return version[0]')

                __localBaseDirectory(serialization: 'custom') {
                    'hudson.FilePath' {
                        'default' {
                            delegate.createNode('remote', "${JENKINS_HOME}/dynamic_parameter/classpath")
                        }
                        delegate.createNode('boolean', true)
                    }
                }

                delegate.createNode('__remoteBaseDirectory', 'dynamic_parameter_classpath')
                delegate.createNode('__classPath', '')
            }
        }
        cpsScm {
            scm {
                git {
                    remote {
                        url('https://github.com/HomeCI/jenkins.git')
                    }
                    branch('$BRANCHSPEC')
                }
            }
            scriptPath("pipelines/webhook/webhookHandler.groovy")
        }
    }
}