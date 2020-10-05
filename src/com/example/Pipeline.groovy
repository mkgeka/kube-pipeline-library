package com.example

class Pipeline {
    def script
    def configurationFile

    Pipeline(script, configurationFile) {
        this.script = script
        this.configurationFile = configurationFile
    }

    def execute() {
	node {
			stage('Clone sources') {
					git url: 'https://github.com/Brialius/test-maven-project.git'
			}
			stage('build') {
					sh "cd project && mvn clean test"
			}
			stage('database') {
					sh "cd database && mvn clean test -Dscope=FlywayMigration"
			}
			stage('deploy') {
					sh "mvn clean install"
			}
			stage('test') {
					sh "cd test && mvn clean test -Dscope=performance"
					sh "cd test && mvn clean test -Dscope=regression"
					sh "cd test && mvn clean test -Dscope=integration"
			}
	}
    }
}
