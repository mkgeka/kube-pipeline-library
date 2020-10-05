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
		    script.stage("read") {
			def checkOutFrom(repo) { git url: "git@github.com:jenkinsci/${repo}" }
		    }
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
