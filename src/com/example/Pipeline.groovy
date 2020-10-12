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
		    def stage = [ 'build', 'database', 'deploy']
		    try {
			    script.stage(stage[0]) {
				    script.env.STAGE_NAME = stage[0]
				    script.println "Current running ${stage[0]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def buildCommand = "ls -la"
				    script.sh "${buildCommand}"
			    }
			    script.stage(stage[1]) {
				    script.env.STAGE_NAME = stage[1]
				    script.println "Current running ${stage[1]}"
				    def databaseFolder = valuesYaml.database.databaseFolder
				    def databaseCommand = "ansible-playbook playbook.yml --check"
				    script.sh "${databaseCommand}"
			    }
			    script.stage(stage[2]) {
				    script.env.STAGE_NAME = stage[2]
				    script.println "Current running ${stage[2]}"
				    def projectFolder = valuesYaml.build.projectFolder
				    def deployCommand = "ansible-playbook playbook.yml"
				    script.sh "${deployCommand}"
			    }
		    }
		    catch(ex) {
			    def recipients = valuesYaml.notifications.email.recipients
			    script.sh 'echo "The stage --- \${STAGE_NAME} --- has been failed the url of the job \${RUN_TESTS_DISPLAY_URL} the url of the pipeline \${JOB_DISPLAY_URL}"'
		    }
	    }
    }
}
