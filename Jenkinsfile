#!groovy

properties([disableConcurrentBuilds(), pipelineTriggers([pollSCM('H/3 * * * *')])])

env.REPO_NAME="github.com/ShiftLeftSecurity/codepropertygraph"

node {
        git poll: true, url: "ssh://git@${env.REPO_NAME}"
	try {
	    stage('cleanUp') {
            	try {
                	deleteDir()
            	} catch (err) {
                	println("WARNING: Failed to delete directory: " + err)
            	}
	    }
	    stage('getSrc') { // for display purposes
      	    // Get code from GitHub repository
      		checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '4b3482c3-735f-4c31-8d1b-d8d3bd889348', url: "ssh://git@${env.REPO_NAME}"]]])
   	    }
   	    stage('preBuild') {
   	    }
  	    stage('runBuild') {
          withEnv(["JAVA_HOME=${ tool 'JDK8u121' }","PATH+MAVEN=${tool 'SBT-0.13.13'}/bin:${env.JAVA_HOME}/bin","COURSIER_TTL=0s"]) {
         		sh "sbt -Dsbt.log.noformat=true 'set protoBindings/ProtobufConfig/protobufProtoc := \"/home/jenkins/tools/protobuf/bin/protoc\"' clean compile publish publish targetHash dependencyHashes codepropertygraph/findbugs"
     	   	}
   	    }
        stage('dtrack') {
            dependencyCheckAnalyzer datadir: '', hintsFile: '', includeCsvReports: false, includeHtmlReports: true, includeJsonReports: false, includeVulnReports: true, isAutoupdateDisabled: false, outdir: 'target/dtrack', scanpath: '**/*.jar', skipOnScmChange: false, skipOnUpstreamChange: false, suppressionFile: '', zipExtensions: ''
        }
   	    stage('archiveBuild') {
   	    }
        stage('archiveResults') {
            publishHTML (target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'target/dtrack', reportFiles: 'dependency-check-report.html', reportName: "DependencyTrack Report"])
            publishHTML (target: [allowMissing: false, alwaysLinkToLastBuild: false, keepAll: true, reportDir: 'target/dtrack', reportFiles: 'dependency-check-vulnerability.html', reportName: "DependencyTrack Vulnerability Report"])
            findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/findbugs/report.xml', unHealthy: ''
            dependencyCheckPublisher canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/dependency-check-report.xml', unHealthy: ''
        }
   	    stage('checkFixed') {
   	    	myBuildNumber = sh(returnStdout: true, script: 'echo $(($BUILD_NUMBER-1))').trim()
   	    	withEnv(["PREV_BUILD_NUMBER=${myBuildNumber}"]) {
   	        	myResult = sh(returnStatus: true, script: 'curl --silent --user admin:1ea44cdc86eefbf888dc2d480a9c9493 http://localhost:8080/job/$JOB_NAME/$PREV_BUILD_NUMBER/api/json | grep -q \"FAILURE\"')
   		}
     		if (myResult) {   
                // placeholder for future use cases
           	} else {  // send fixed email
               	slackSend (channel: '#team-code-science', color: '#22FF00', message: "FIXED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
               	emailext body: 'Build URL: $BUILD_URL (to view full results, click on "Console Output")', attachLog: true, recipientProviders: [[$class: 'CulpritsRecipientProvider']], subject: 'Notice: Jenkins $JOB_NAME #$BUILD_NUMBER FIXED!', to: 'build-notify-code-science@shiftleft.io'
           	}
   	     }
         stage('triggerBuilds') {
            build job: 'CI-MASTER-Java2CPG', propagate: false, wait: false
            build job: 'CI-MASTER-Proto', propagate: false, wait: false
         }
	} catch (e) {
	    	currentBuild.result = "FAILED"
		notifyFailed()
	} 
}

def notifyFailed() {
	slackSend (channel: '#team-code-science', color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
	emailext body: 'Build URL: $BUILD_URL (to view full results, click on "Console Output")', attachLog: true, recipientProviders: [[$class: 'CulpritsRecipientProvider']], subject: 'Action Required: Jenkins $JOB_NAME #$BUILD_NUMBER FAILED', to: 'build-notify-code-science@shiftleft.io'
}
