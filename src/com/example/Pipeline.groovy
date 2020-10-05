package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

	return this
    def execute() {
	    script.node("master") {
		    script.stage("source") {
			def array = new File(configurationFile) as String[]

		    }
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
