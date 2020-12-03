properties([
  [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false],
  parameters([
    choice(
      choices: ['', 'optionA', 'optionB', 'optionC'],
      description: 'Choice parameter defined in pipeline',
      name: 'param1'
    )
  ]),
  pipelineTriggers([cron('H 23 * * *')])])

node {
  //random executor
}

node("k8s-aio") {
  container('aio') {
    
    stage('checkout'){
      git branch:"master", url: 'https://github.com/Lianidaz/crash-course-jenkins.git', credentialsId: 'github'

      checkout([
        $class: 'GitSCM', branches: [[name: '*/master']],
        doGenerateSubmoduleConfigurations: false,
        extensions: [[
          $class: 'RelativeTargetDirectory',
          relativeTargetDir: 'subdir'
        ]],
        userRemoteConfigs: [[
          credentialsId: 'github-infra',
          url: 'https://github.com/Lianidaz/vault-cli.git'
      ]]])
    }

    stage('test') {
      sh script: "npm install"
      def stdout = sh(script: "npm run test", returnStdout: true)

      sh script: 'BOO=FAR'
      sh script: "echo \$BOO" // BOO does not exist in this shell
    }
    
    stage('build'){
      def status = sh(script: "npm run build", returnStatus: true)
      if (status!=0) error('Build failed!')
    }

    stage('in other directory') {
      dir('subdir') {
        sh script: "pwd" //will print directory $WORKSPACE/subdir
        withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'pass', usernameVariable: 'user')]) {
          sh script: "docker login -u $user -p $pass"
        }
        withEnv(['FOO=BAR']) {
          sh script: 'echo $FOO'
        }
      }
    }

    stage('readFile') {
      def file = readFile 'subdir/file.txt'
    }

    stage('build another') {
      build job: 'droneBuilder', parameters: [string(name: 'runtime', value: 'node10')]

      parallel firstBranch: {
        build job: 'build1'
      }, secondBranch: {
        build job: 'build2'
      },
      failFast: true
    }
  }
}