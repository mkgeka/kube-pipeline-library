package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	    script.stage("source")
	    script.node("test") 
	    script.node("Build")
	    script.node("Deploy")
    }
}
