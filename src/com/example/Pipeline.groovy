package com.example
import java.util.*;

class Pipeline {
    def script
    def configurationFile
	
    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    script.node("master") {
		    script.git("https://github.com/mkgeka/test-maven-project.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    try {
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
			    def i = 0
			    def testFolder = valuesYaml.test.testFolder
			    def name = valuesYaml.test.name
			    def testCommand = valuesYaml.test.testCommand
			    def arrayLength = name.size()
			    for (i = 0; i <arrayLength; i++) { script.sh "cd ${testFolder[i]} && ${testCommand[i]}" }
			    script.println STAGE_NAME
		    }
            }
		    catch(all) {
			    def recipients = valuesYaml.notifications.email.recipients 
		    }
	    }
    }
}
