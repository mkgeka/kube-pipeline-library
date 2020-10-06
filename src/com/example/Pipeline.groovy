package com.example

class Pipeline {
    def script
    def configurationFile
    def valuesYaml
	
    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    script.node("master") {
		script.git("https://github.com/mkgeka/test-maven-project.git")
		def valuesYaml = script.readYaml(file: configurationFile)
		    //script.stage("notifications") { script.step([$class: valuesYaml.notifications]) }
		    //script.stage("database") { script.step(valuesYaml.database) }
		    //script.stage("test") { script.step(valuesYaml.test) }
		    script.stage("build") { script.step() { [$class: valuesYaml.build] } }
	    }
    }
}
