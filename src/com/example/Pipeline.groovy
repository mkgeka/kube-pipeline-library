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
		script.git("https://github.com/Brialius/test-maven-project.git")
		    script.stage("read") {
			    def array = new File(configurationFile) as String[]
		    }
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
