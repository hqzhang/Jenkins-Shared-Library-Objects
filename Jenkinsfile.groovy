@Library('Jenkins-Shared-Library-Objects@with-shared-library')

def consul = new org.foo.Consul()
pipeline {
    agent any
    environment{
        String jsonRegisterData = "{ .. }" // A string whatever data we want in json format
        String path = "http://myconsul.com:8500/v1"
    }
    stages {
        stage("register data") {
            steps {
                script {
                     consul.register(path, jsonRegisterData)
                }
            }
        }

        stage("store some data") { 
            steps {
                script {
            consul.store(path, "v1/kv/test/1/data", "value1")
            consul.store(path, "v1/kv/test/2/data", "value2")
          }
            }
        }
        stage("list Nodes"){
             steps {
                script {
            consul.list_nodes(path)
         }
            }
        }
        
    }
}

        
