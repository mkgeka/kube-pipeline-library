package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	    script.node("source1")
	    script.stage("source")
	    script.stage("test") 
	    script.stage("Build")
	    script.stage("Deploy")
    }
}
