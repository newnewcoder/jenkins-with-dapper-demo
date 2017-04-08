#!/usr/bin/env groovy
node {
    stage('checkout'){
        checkout scm
    }
    stage('build'){
        sh 'dapper'
    }
    stage('deploy'){
        sh 'chmod +x gradlew'
        sh './gradlew deploy'
    }
}