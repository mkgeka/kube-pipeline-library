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
		    script.stage("database") {
			    def databaseCommand = valuesYaml.databaseCommand
			    script.sh "echo ${databaseCommand}" }
		    script.stage("test") { 
			    def databaseCommand = valuesYaml.databaseCommand
			    script.sh "echo \${databaseCommand}" }
		    script.stage("build") { 
			    def databaseCommand = valuesYaml.databaseCommand
			    script.sh "echo \$databaseCommand" }
		    script.stage("build1") { 
			    def databaseCommand = valuesYaml.databaseCommand
			    script.sh "echo $databaseCommand" }
	    }
    }
}
