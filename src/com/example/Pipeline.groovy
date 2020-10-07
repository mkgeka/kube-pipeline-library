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
		    script.git("https://github.com/mkgeka/test-maven-project.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    def databaseCommand = valuesYaml.databaseCommand
		    script.stage("database") { script.sh 'echo ${valuesYaml.databaseCommand}' }
		    script.stage("test") { valuesYaml.test }
		    script.stage("build") { valuesYaml.build }
	    }
    }
}
