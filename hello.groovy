pipelineJob('hello') {
    description('Pipeline template de ejemplo')
    parameters {
        stringParam {
            name('message')
            defaultValue('Hello Jenkins')
            description('Mensaje de saludo')
            trim(true)
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
                        url('https://git.svb.lacaixa.es/cbk/k8s/kfkalm/kfkpip.git')
                        credentials('jenkins-dummy-user')
                    }
                    branch('$BRANCHSPEC')
                }
            }
            scriptPath("pipelines/examples/deployTopic/hello.grooy")
        }
    }
}