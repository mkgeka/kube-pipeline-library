package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    script.node("master") {
		script.git("https://github.com/mkgeka/test-maven-project.git")
		script.readYaml(file: configurationFile)
		script.stage("notifications")
		script.stage("build")
		script.stage("database")
		script.stage("deploy")
		script.stage("test")
	    }
    }
}
