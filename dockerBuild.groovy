pipelineJob('DockerBuild') {
    description(" 🐋 Realiza las gestiones de docker build, promoción de la imagen al registry y deploy del docker-compose")
    parameters {
        stringParam {
            name('gitrepourl')
            defaultValue('')
            description('Url del repositorio `https://github.com/.../...` ')
        }
        stringParam {
            name('tagname')
            defaultValue('')
            description('Nombre de la etiqueta para la imagen de Docker')
        }
        booleanParam {
            name('deploy')
            defaultValue(true)
            description('¿Realizar el despliegue de la imagen de Docker?')
        }
        booleanParam {
            name('dbuild')
            defaultValue(true)
            description('¿Construir la imagen de Docker?')
        }
        stringParam {
            name('dockerfilePath')
            defaultValue('.')
            description('Ruta del archivo Dockerfile')
        }
        stringParam {
            name('dockercomposePath')
            defaultValue('.')
            description('Ruta del archivo docker-compose')
        }
        stringParam {
            name('credentialsId')
            defaultValue('')
            description('ID de las credenciales para el registro de Docker')
        }
        booleanParam {
            name('pullToRegistry')
            defaultValue(false)
            description('¿Realizar la carga de la imagen a un registro de Docker?')
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
            scriptPath("pipelines/docker/dockerBuild.groovy")
        }
    }
}