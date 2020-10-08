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
		    try {
		    script.stage(stage[0]) {
			    def projectFolder = valuesYaml.stage\[0\].projectFolder
			    def buildCommand = valuesYaml.stage[0].buildCommand
			    script.dir(projectFolder) { script.sh "${buildCommand}" }
		    }
		    script.stage(stage[1]) {
			    def databaseFolder = valuesYaml.stage\[1\].databaseFolder
			    def databaseCommand = valuesYaml.stage[1].databaseCommand
			    script.dir(databaseFolder) { script.sh "${databaseCommand}" } 
		    }
		    script.stage(stage[2]) {
			    def projectFolder = valuesYaml.stage\[0\].projectFolder
			    def deployCommand = valuesYaml.stage\[2\].deployCommand
			    script.dir(projectFolder) { script.sh "${deployCommand}" }
		    }
		    script.stage(stage[3]) {
			    def testFolder = valuesYaml.stage\[3\].testFolder
			    def name = valuesYaml.stage\[3\].name
			    def testCommand = valuesYaml.stage\[3\].testCommand
			    def arrayLength = name.size()
			    for (def i = 0; i <arrayLength; i++) { script.dir(testFolder[i]) { script.sh "${testCommand[i]}" } }
		    }
            }
		    catch(all) {
			    def recipients = valuesYaml.notifications.email.recipients
			    script.println "The stage ${stage[0]} has been failed so sending email to ${recipients}"
		    }
	    }
    }
}
