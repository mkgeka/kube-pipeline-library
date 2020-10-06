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
		script.stage("read") {  def valuesYaml = script.readYaml(file: configurationFile); return valuesYaml }
		script.stage("notifications")
		script.stage("build") { script.steps { valuesYaml.buildCommand } }
		script.stage("database")
		script.stage("deploy")
		script.stage("test")
	    }
    }
}
