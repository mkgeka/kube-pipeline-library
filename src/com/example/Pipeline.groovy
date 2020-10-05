package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }
    def checkOutFrom(repo) {
  		git url: "https://github.com/mkgeka/test-maven-project.git"
	}

	return this
    def execute() {
	    script.node("master") {
		    script.stage("source") {
			def z = new org.foo.Zot()
			z.checkOutFrom(repo)  
		    }
		script.stage("Build")
	    	script.stage("Deploy")
	    }
    }
}
