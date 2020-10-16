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
		    script.git("https://github.com/mkgeka/kube-maven-jenkinsfile.git")
		    def valuesYaml = script.readYaml(file: configurationFile)
		    def stage = [ 'source', 'preparing', 'build', 'test image', 'test playbooks', 'deploy']
		    try {
			    script.stage(stage[0]) {
				    script.env.STAGE_NAME = stage[0]
				    script.println "Current running ${stage[0]}"
				    def sourceCommand = "ls -la"
				    script.sh "${sourceCommand}"
			    }
			    script.stage(stage[1]) {
				    script.env.STAGE_NAME = stage[1]
				    script.println "Current running ${stage[1]}"
				    script.sh "ansible-galaxy install geerlingguy.jenkins"
				    script.sh "ansible-galaxy install geerlingguy.docker"
				    script.sh "ansible-galaxy collection install community.general"
			    }
			    script.stage(stage[2]) {
				    script.env.STAGE_NAME = stage[2]
				    script.println "Current running ${stage[2]}"
				    def buildCommand = valuesYaml.build.buildCommand
				    script.sh "${buildCommand}"
			    }
			    script.stage(stage[3]) {
				    script.env.STAGE_NAME = stage[3]
				    script.println "Current running ${stage[3]}"
				    def testImage = valuesYaml.test.testImage
				    script.sh "${testImage}"
			    }
			    
			    script.stage(stage[4]) {
				    script.env.STAGE_NAME = stage[4]
				    script.println "Current running ${stage[4]}"
				    def testCommand = valuesYaml.test.testCommand
				    script.sh "${testCommand}"
			    }
			    script.stage(stage[5]) {
				    script.env.STAGE_NAME = stage[5]
				    script.println "Current running ${stage[5]}"
				    def deployCommand = valuesYaml.deploy.deployCommand
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
