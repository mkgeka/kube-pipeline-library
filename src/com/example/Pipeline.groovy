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
			    script.sh "cd ${projectFolder} && ${buildCommand}" 
		    }
		    script.stage("database") {
			    def databaseFolder = valuesYaml.database.databaseFolder
			    def databaseCommand = valuesYaml.database.databaseCommand
			    script.sh "cd ${databaseFolder} && ${databaseCommand}" 
		    }
		    script.stage("deploy") {
			    def projectFolder = valuesYaml.build.projectFolder
			    def deployCommand = valuesYaml.deploy.deployCommand
			    script.sh "cd ${projectFolder} && ${deployCommand}" 
		    }
		    script.stage("test") {
			    def testFolder = [ valuesYaml.test.testFolder as String ]
			    def name = [ valuesYaml.test.name as String ]
			    def testCommand = [ valuesYaml.test.testCommand as String ]
			    def arrayLength = name.size()
			    script.echo arrayLength
			    //for (i = 0; i <=arrayLength; i++) { script.sh "echo ${testFolder[i]}" }
		    }
	    }
    }
}
