package com.example
import java.util.*;

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
		    script.stages() {
			script.git("https://github.com/mkgeka/test-maven-project.git")
			def valuesYaml = script.readYaml(file: configurationFile)
		    	script.stage("notifications") { script.steps(valuesYaml.notifications) }
		    	script.stage("database") { valuesYaml.database }
		    	script.stage("test") { valuesYaml.test }
		    	script.stage("build") { valuesYaml.build }
		    }
	    }
    }
}
