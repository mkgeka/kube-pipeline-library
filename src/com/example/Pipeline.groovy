package com.example
import java.util.*;

class Pipeline {
    def script
    def configurationFile
    //def valuesYaml
	
    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    script.node("master") {
		    script.git("https://github.com/mkgeka/test-maven-project.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    script.stage("build") {
			    def projectFolder = valuesYaml.build.projectFolder
			    def buildCommand = valuesYaml.build.buildCommand
			    script.echo buildCommand projectFolder
			    script.sh "cd ${projectFolder} && ${buildCommand}" 
		    }
	    }
    }
}
