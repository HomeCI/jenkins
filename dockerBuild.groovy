pipelineJob('DockerBuild') {
    parameters {
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
        cps {
            script("""
                library 'hci@main'
                dockerCore tagname:\${params.tagname}
                           deploy: \${params.deploy}
                           build: \${params.dbuild}
                           dockerfilePath: \${params.dockerfilePath}
                           dockercomposePath: \${params.dockercomposePath}
                           credentialsId: \${params.credentialsId}
                           pullToRegistry: \${params.pullToRegistry}
            """)
        }
    }
}
