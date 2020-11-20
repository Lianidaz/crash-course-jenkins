properties([
  parameters([
    choice(choices: ['', 'docker', 'node10', 'terraform', 'allinone'], description: '', name: 'runtime')
])])

node('k8s-aio'){
  container('aio'){
    stage('checkout') {
      git branch:"master", url: 'https://github.com/Lianidaz/crash-course-jenkins.git'
    }
    stage('build'){
      sh script:"docker build -t liandiaz/jenkins-drone:${params.runtime} -f drones/${params.runtime}.dockerfile . && docker push liandiaz/jenkins-drone:${params.runtime}"
    }
  }
}