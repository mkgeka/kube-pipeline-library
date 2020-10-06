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
		    script.stage("Initialize") { valuesYaml.getClass()  }
		    script.stage("notifications") { valuesYaml.notifications }
		    script.stage("build") { script.step( valuesYaml.build ) }
		    script.stage("database") { script.step( valuesYaml.database ) }
		    script.stage("deploy") { script.step( valuesYaml.deploy ) }
		    script.stage("test") { script.step( valuesYaml.test ) }
	    }
    }
}
