package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def execute() {
	    git url: "git@github.com:jenkinsci/${repo}
	    script.node("master") {
		    script.stage("read")
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
