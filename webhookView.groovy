listView('HCI Webhooks') {
    description('Webhooks')
    jobs {
        name('WebhookOrchestrator')
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