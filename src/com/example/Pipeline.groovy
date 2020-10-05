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
		    script.stage("read"){
			script.readFile(configurationFile)
		    }
		    script.stage("notifications") {
			script.sh("pwd")
		    }
	    	script.stage("build")
		    script.sh('grep "buildCommand' config.yml | awk {'print$2'}")
		script.stage("database")
		script.stage("deploy")
		script.stage("test")
	    }
    }
}
