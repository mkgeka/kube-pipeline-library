package com.example
import java.util.*;

class Pipeline {
    def script
    def configurationFile
    def valuesYaml
    def buildCommand = 1
	
    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    println buildCommand
	    script.node("master") {
		    script.git("https://github.com/mkgeka/test-maven-project.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    script.stage("build") {
			    def buildCommand = valuesYaml.buildCommand
			    script.sh "echo ${buildCommand}" 
		    }
	    }
    }
}
