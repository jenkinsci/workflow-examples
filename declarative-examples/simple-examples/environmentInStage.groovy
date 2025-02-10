pipeline {
  agent { label 'linux' }

  environment {
    // FOO will be available in entire pipeline
    FOO = "PIPELINE"
  }

  stages {
    
    stage('CheckOutGit') {
	    steps{
		    checkout([$class: 'GitSCM',
		    branches: [[name: 'master']],
		    doGenerateSubmoduleConfigurations: false,
		    extensions: [],
		    submoduleCfg: [],
		    userRemoteConfigs: [[credentialsId: 'Git',
		    url: 'git@github.com:ajinkyamhasrup/pipeline-examples.git']]])
	    }
}
    
    stage("local") {
      environment {
        // BAR will only be available in this stage
        BAR = "STAGE"
      }
      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
      }
    }
    stage("global") {
      steps {
        sh 'echo "FOO is $FOO and BAR is $BAR"'
      }
    }

  }
}
