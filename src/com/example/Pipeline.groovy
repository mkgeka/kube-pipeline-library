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
	    	script.node("performance") 
	    }
	    script.node("Build")
	    script.node("Deploy")
    }
}
