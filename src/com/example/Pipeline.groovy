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
	    def server = "master"
	    script.node(server) {
		    script.git("https://github.com/mkgeka/test-maven-project.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    def stage = [ 'build', 'database', 'deploy', 'test']
		    try {
			    script.stage(stage[0]) {
				    script.environment  { STAGE_NAME = "123" }
				    script.println "Current running ${stage[0]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def buildCommand = valuesYaml.build.buildCommand
				    script.dir(projectFolder) { script.sh "${buildCommand}" }
			    }
			    script.stage(stage[1]) {
				    script.println "Current running ${stage[1]}"
				    def databaseFolder = valuesYaml.database.databaseFolder
				    def databaseCommand = valuesYaml.database.databaseCommand
				    script.dir(databaseFolder) { script.sh "${databaseCommand}" } 
			    }
			    script.stage(stage[2]) {
				    script.environment  { STAGE_NAME = "123" }
				    script.println "Current running ${stage[2]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def deployCommand = valuesYaml.deploy.deployCommand
				    script.dir(projectFolder) { script.sh "${deployCommand}" }
			    }
			    script.stage(stage[3]) {
				    script.environment  { STAGE_NAME = "123" }
				    script.println "Current running ${stage[3]}"
				    def testFolder = valuesYaml.test.testFolder
				    def name = valuesYaml.test.name
				    def testCommand = valuesYaml.test.testCommand
				    def arrayLength = name.size()
				    def builders = [:]
				    for (def i = 0; i <arrayLength; i++) { 
					def WorkFolder = testFolder[i]
					def WorkCommand = testCommand[i]
					builders[i] = { script.dir(WorkFolder) { script.sh "${WorkCommand}" } } }
				    script.parallel builders
			    }
		    }
		    catch(ex) {
			    def recipients = valuesYaml.notifications.email.recipients
			    script.sh 'echo "The stage \${STAGE_NAME} has been failed the url of the job \${RUN_TESTS_DISPLAY_URL} the url of the pipeline \${JOB_DISPLAY_URL}"'
		    }
	    }
    }
}
