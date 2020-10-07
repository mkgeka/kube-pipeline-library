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
			    def projectFolder = valuesYaml.database.projectFolder
			    def deployCommand = valuesYaml.database.deployCommand
			    script.sh "cd ${projectFolder} && ${deployCommand}" 
		    }
		    script.stage("test") {
			    def testFolder = valuesYaml.database.testFolder
			    def testCommand = valuesYaml.database.testCommand
			    def name = valuesYaml.database.name
			    script.echo testFolder
			    script.echo testCommand
			    script.echo name
			    //script.sh "cd ${testFolder} && ${testCommand}" 
		    }
	    }
    }
}
