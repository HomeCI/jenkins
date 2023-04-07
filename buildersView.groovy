listView('Builders') {
    description('Builders')
    jobs {
        name('DockerBuild')
    }

    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}