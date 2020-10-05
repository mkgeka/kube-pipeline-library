package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	    script.stage("source") {
		   git url: 'https://github.com/Brialius/test-maven-project.git'
	    }
	    script.stage("test") 
	    script.stage("Build")
	    script.stage("Deploy")
    }
}
