pipeline{
    #any:���κο��õĴ�����ִ����ˮ�߻�׶�
    #none:ÿ�� stage ���ֶ���Ҫ�������Լ��� agent
    #label:�ṩ�˱�ǩ�� Jenkins �����п��õĴ�����ִ����ˮ�߻�׶� agent { label 'my-defined-label' }
    #node: ��labelһ��,node ��������ѡ�� (���� customWorkspace )��
    agent : any
    #�û��ڴ���pipelineʱӦ���ṩ�Ĳ����б�
    parameters : {
        boolean(name: 'skipSonar', defaultValue: false, description: '����sonar���,Ĭ��: false'),
        boolean(name: 'skipTest', defaultValue: false, description: '������Ԫ����,Ĭ��: false'),
        boolean(name: 'skipDocker', defaultValue: false, description: '��������������,Ĭ��: false'),
        boolean(name: 'skipDeploy', defaultValue: false, description: '��������,Ĭ��: false'),
    }
    options: {
        //Ϊ�������ˮ�����е��ض�������������Ϳ���̨���
        buildDiscarder(logRotator(numToKeepStr: '1'))
    }
    stages{
        stage('start'){
            steps{
                echo "start"
            }
        }
    }
    
    agent: node{
        label: 'maven'
    }
    options: {
        timestamps(5,unit: 'second')
    }
    stages: {
        stage('env'){
            script{
                
            }
        }
        stage('scm checkout'){
            
        }
    }
}