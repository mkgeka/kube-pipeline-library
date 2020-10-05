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
		script.stage("source")
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
