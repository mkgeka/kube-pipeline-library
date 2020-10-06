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
		    // script.stage("notifications") { valuesYaml.getClass() }
		    script.stage("build") { script.step(valuesYaml.getClass().valuesYaml.build) }
		    script.stage("database") { valuesYaml.database }
		    script.stage("deploy") { valuesYaml.deploy }
		    script.stage("test") { valuesYaml.test }
	    }
    }
}
