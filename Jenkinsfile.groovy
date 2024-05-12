//@Library('Jenkins-Shared-Library-Objects@with-shared-library')
def repo = scm.getUserRemoteConfigs()[0].toString()
repo = repo.replace(' (null)','').replace('null => ','')
def brch= scm.branches[0].toString().replace('*/','')
def shlib = library identifier: "Jenkins-Shared-Library-Objects@with-shared-library", retriever: modernSCM(
  [$class: 'GitSCMSource', remote: repo, credentialsId: ''])
def consul = new shlib.org.foo.Consul()


node('NODE') {
    try {

        String jsonRegisterData = "{ .. }" // A string whatever data we want in json format
        
        String path = "http://myconsul.com:8500/v1"

        stage("register data") {
            consul.register(path, jsonRegisterData)
        }

        stage("store some data") {
            consul.store(path, "v1/kv/test/1/data", "value1")
            consul.store(path, "v1/kv/test/2/data", "value2")
        }

        stage("list Nodes"){
            consul.list_nodes(path)
        }

    }

    catch(err){
        //.. handle
    }
}
