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
			    script.dir(projectFolder) { script.sh "${buildCommand}" }
		    }
		    script.stage("database") {
			    def databaseFolder = valuesYaml.database.databaseFolder
			    def databaseCommand = valuesYaml.database.databaseCommand
			    script.dir(databaseFolder) { script.sh "${databaseCommand}" } 
		    }
		    script.stage("deploy") {
			    def projectFolder = valuesYaml.build.projectFolder
			    def deployCommand = valuesYaml.deploy.deployCommand
			    script.dir(projectFolder) { script.sh "${deployCommand}" }
		    }
		    script.stage("test") {
			    script.println "The STAGE is: ${STAGE_NAME}"
			    def i = 0
			    def testFolder = valuesYaml.test.testFolder
			    def name = valuesYaml.test.name
			    def testCommand = valuesYaml.test.testCommand
			    def arrayLength = name.size()
			    for (i = 0; i <arrayLength; i++) { script.dir(testFolder[i]) { script.sh "${testCommand[i]}" } }
		    }
            }
		    catch(all) {
			    def recipients = valuesYaml.notifications.email.recipients
			    script.println "The stage has been failed so sending email to ${recipients}"
		    }
	    }
    }
}
