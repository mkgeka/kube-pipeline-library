package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	// 
    }
}

def checkOutFrom(repo) {
  git url: "https://github.com/mkgeka/test-maven-project.git"
}

return this
