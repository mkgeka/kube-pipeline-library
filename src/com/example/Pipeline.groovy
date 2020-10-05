package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	    script.node("master") {
		    script.stage("source") {
			    git changelog: true, poll: false,
                        	branch: 'master',
                        	url: "https://github.com/Brialius/test-maven-project.git"
		    }
	    }
	    script.stage("Build")
	    script.stage("Deploy")
    }
}
