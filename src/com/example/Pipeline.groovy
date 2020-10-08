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
		    def stage = [ 'build', 'database', 'deploy', 'test']
		    script.stage(stage[0]) {
			    try {
			    def projectFolder = valuesYaml.build.projectFolder
			    def buildCommand = valuesYaml.build.buildCommand
			    script.dir(projectFolder) { script.sh "${buildCommand}" }
			    }
			    catch(all) {
			    	def recipients = valuesYaml.notifications.email.recipients
			    	script.println "The stage ${stage[0]} has been failed so sending email to ${recipients}"
			    }
		    }
		    script.stage(stage[1]) {
			    try {
			    def databaseFolder = valuesYaml.database.databaseFolder
			    def databaseCommand = valuesYaml.database.databaseCommand
			    script.dir(databaseFolder) { script.sh "${databaseCommand}" } 
			    }
			    catch(all) {
			    	def recipients = valuesYaml.notifications.email.recipients
			    	script.println "The stage ${stage[1]} has been failed so sending email to ${recipients}"
			    }
		    }
		    script.stage(stage[2]) {
			    try {
			    def projectFolder = valuesYaml.build.projectFolder
			    def deployCommand = valuesYaml.deploy.deployCommand
			    script.dir(projectFolder) { script.sh "${deployCommand}" }
			    }
			    catch(all) {
			    	def recipients = valuesYaml.notifications.email.recipients
			    	script.println "The stage ${stage[2]} has been failed so sending email to ${recipients}"
			    }
		    }
		    script.stage(stage[3]) {
			    try {
			    def testFolder = valuesYaml.test.testFolder
			    def name = valuesYaml.test.name
			    def testCommand = valuesYaml.test.testCommand
			    def arrayLength = name.size()
			    for (def i = 0; i <arrayLength; i++) { script.dir(testFolder[i]) { script.sh "${testCommand[i]}" } }
			    }
			    catch(all) {
			    	def recipients = valuesYaml.notifications.email.recipients
			    	script.println "The stage ${stage[3]} has been failed so sending email to ${recipients}"
			    }
		    }
	    }
    }
}
