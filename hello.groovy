pipelineJob('my-pipeline-job') {
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
                // Aquí puedes agregar los pasos que quieras para construir y desplegar tu imagen de Docker
                echo "El valor de tagname es: \${params.tagname}"
                echo "El valor de deploy es: \${params.deploy}"
                echo "El valor de dbuild es: \${params.dbuild}"
                echo "La ruta del Dockerfile es: \${params.dockerfilePath}"
                echo "La ruta del archivo docker-compose es: \${params.dockercomposePath}"
                echo "El ID de las credenciales es: \${params.credentialsId}"
                echo "El valor de pullToRegistry es: \${params.pullToRegistry}"
            """)
        }
    }
}
