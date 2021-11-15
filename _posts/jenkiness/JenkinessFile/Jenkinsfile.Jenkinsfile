pipeline{
    #any:在任何可用的代理上执行流水线或阶段
    #none:每个 stage 部分都需要包含他自己的 agent
    #label:提供了标签的 Jenkins 环境中可用的代理上执行流水线或阶段 agent { label 'my-defined-label' }
    #node: 与label一样,node 允许额外的选项 (比如 customWorkspace )。
    agent : none
    options: {
        //为最近的流水线运行的特定数量保存组件和控制台输出
        buildDiscarder(logRotator(numToKeepStr: '1'))
    }
    stages{
        stage('config'){
            steps{
                
            }
        }
    }
}