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
				    script.println ${STAGE_NAME}
				    script.println "Current running ${stage[0]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def buildCommand = valuesYaml.build.buildCommand
				    script.dir(projectFolder) { script.sh "${buildCommand}" }
				    return failed_stage
			    }
			    script.stage(stage[1]) {
				    script.println "Current running ${stage[1]}"
				    def databaseFolder = valuesYaml.database.databaseFolder
				    def databaseCommand = valuesYaml.database.databaseCommand
				    script.dir(databaseFolder) { script.sh "${databaseCommand}" } 
			    }
			    script.stage(stage[2]) {
				    script.println "Current running ${stage[2]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def deployCommand = valuesYaml.deploy.deployCommand
				    script.dir(projectFolder) { script.sh "${deployCommand}" }
			    }
			    script.stage(stage[3]) {
				    script.println "Current running ${stage[3]}"
				    def testFolder = valuesYaml.test.testFolder
				    def name = valuesYaml.test.name
				    def testCommand = valuesYaml.test.testCommand
				    def arrayLength = name.size()
				    for (def i = 0; i <arrayLength; i++) { script.dir(testFolder[i]) { script.sh "${testCommand[i]}" } }
			    }
		    }
		    catch(all) {
			    def recipients = valuesYaml.notifications.email.recipients
			    script.println recipients 
		    }
	    }
    }
}
