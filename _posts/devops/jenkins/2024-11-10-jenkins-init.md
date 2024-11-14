---
layout: post
title: Jenkins(三)初始化脚本
category: jenkins
tags: [life]
no-post-nav: true
---

## 初始化脚本

在jenkins启动过程中,我们希望他能够自动配置一些信息,可以避免我们后续手动配置,做到整个jenkins开箱即用

### 1. 默认设置admin账号

```groovy
#!/usr/bin/env groovy
import jenkins.model.*
import hudson.security.*

//默认设置admin账号
def instance = Jenkins.getInstance()

def admin = System.getenv('ADMIN_USER')
def password = System.getenv('ADMIN_PASSWORD')

println "==============创建admin用户开始========================"

def hudsonRealm = new HudsonPrivateSecurityRealm(false) as java.lang.Object
hudsonRealm.createAccount(admin,password)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy() as java.lang.Object
instance.setAuthorizationStrategy(strategy)

instance.save()

println "==============创建admin用户结束========================"
```

### 2. 定义环境变量

```groovy
#!/usr/bin/env groovy
import jenkins.model.*
import hudson.security.*
import hudson.slaves.*

def instance = Jenkins.getInstance()

def DOCKER_REGISTRY_URL = System.getenv('DOCKER_REGISTRY_URL')
def DOCKER_REPOSITORY_URL = System.getenv('DOCKER_REPOSITORY_URL')

println "==============创建环境变量========================"


// 获取或创建一个EnvironmentVariablesNodeProperty实例
def properties = instance.getGlobalNodeProperties();
def envVarsProp = properties.get(EnvironmentVariablesNodeProperty)

if (envVarsProp == null) {
    envVarsProp = new EnvironmentVariablesNodeProperty()
}

// 添加新的环境变量到属性列表
envVarsProp.getEnvVars().put("DEV_IN_BANK", "true")
if (DOCKER_REGISTRY_URL != null && DOCKER_REPOSITORY_URL != null) {
    envVarsProp.getEnvVars().put("DOCKER_PREFIX", DOCKER_REGISTRY_URL + "/" + DOCKER_REPOSITORY_URL)
}


properties.remove(envVarsProp.getClass())
properties.add(envVarsProp)

instance.save()

println "==============创建环境变量结束========================"
```

### 3. 创建Gitlab认证信息

```groovy
import jenkins.*
import hudson.*
import hudson.model.*
import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*

credentialId="gitlab-builder"
credentialDescription="builder user for gitlab"
credentialUser=System.getenv('BUILDER_USER')
credentialPassword=System.getenv('BUILDER_PASSWORD')

println "==============创建gitlab证书开始========================"

def instance = Jenkins.getInstance()

global_domain = Domain.global()
credentials_store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

credentials = new UsernamePasswordCredentialsImpl(
        CredentialsScope.GLOBAL,
        credentialId,
        credentialDescription,
        credentialUser,
        credentialPassword)

credentials_store.addCredentials(global_domain, credentials)


println "==============创建gitlab证书结束========================"
```

### 4. 添加JDK工具

```groovy
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

println "==============安装JDK工具开始========================"

def instance = Jenkins.getInstance()
def jdkTool = instance.getDescriptor("hudson.model.JDK")

def oracleJdk8 = new JDK("oracle-jdk8", "/opt/java/oracle-jdk8") as java.lang.Object;
def openJdk11 = new JDK("jdk11", "/opt/java/openjdk11") as java.lang.Object;
def openjdk17 = new JDK("jdk17", "/opt/java/openjdk") as java.lang.Object;

jdkTool.setInstallations(oracleJdk8,openjdk17,openJdk11)
jdkTool.save()

println "==============安装JDK工具结束========================"
```

### 5. 添加maven工具

```groovy
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*

println "==============安装MAVEN工具开始========================"

def instance = Jenkins.getInstance()
def mavenTool = instance.getDescriptor("hudson.tasks.Maven")
def maven3 = new hudson.tasks.Maven.MavenInstallation("maven3", "/opt/maven/apache-maven-3.8.4") as java.lang.Object
mavenTool.installations += maven3
mavenTool.save()

println "==============安装MAVEN工具结束========================"


```

### 6. 添加子节点

```groovy
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import hudson.slaves.*
import hudson.util.*


nodeNum = Integer.parseInt(System.getenv('NODE_NUM'))

println "==============新建子节点开始========================"

def instance = Jenkins.getInstance()

for (i = 1; i < nodeNum + 1; i++) {
    // 配置代理节点的参数
    def nodeParams = [
            name             : "node${i}",
            remoteFS         : '/home/jenkins/workspace', // 代理节点上的工作空间路径
            labels           : 'maven', // 为节点分配的标签
            mode             : Node.Mode.NORMAL,
            retentionStrategy: RetentionStrategy.Always.INSTANCE, // 保留策略，这里始终保留
            numExecutors     : "4", // 节点上可用的执行器数量
    ] as java.lang.Object

    // 配置JNLP连接方式
    def jnlpLauncher = new JNLPLauncher("") as java.lang.Object

    // 创建节点
    def slave = new DumbSlave(nodeParams.name.toString(), nodeParams.name.toString(), nodeParams.remoteFS, nodeParams.numExecutors, nodeParams.mode, nodeParams.labels, jnlpLauncher, nodeParams.retentionStrategy) as java.lang.Object
    instance.addNode(slave)

    // 保存配置
    instance.save()
}


println "==============新建子节点结束========================"
```

### 7. 共享library

```groovy
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import org.jenkinsci.plugins.workflow.libs.*
import jenkins.plugins.git.GitSCMSource

sharedLibraryRepo=System.getenv('SHARED_LIBRARY_REPO')
String credentialsId = "gitlab-builder"

println "==============设置共享仓库开始========================"

def instance = Jenkins.getInstance()

// 确保 Jenkins 实例处于锁定状态，以便安全地进行配置修改
instance.setQuietPeriod(0)
instance.save()

// 创建或更新全局的共享库配置
def libConfig = new LibraryConfiguration("spLibrary", new SCMSourceRetriever(new GitSCMSource(null, sharedLibraryRepo, credentialsId, "*", "", false))) as java.lang.Object
libConfig.setDefaultVersion("master")

// 全局变量中添加或更新共享库配置
def globalLibraries = GlobalLibraries.get()
globalLibraries.setLibraries([libConfig])
globalLibraries.save()

println "==============共享仓库设置成功: mySharedLibrary========================"

// 重置Jenkins实例的安静期
instance.setQuietPeriod(5)
instance.save()

println "==============设置共享仓库结束========================"
```

### 8.自动创建多分支流水线

```groovy
#!/usr/bin/env groovy
import jenkins.*
import jenkins.model.*
import hudson.*
import hudson.model.*
import hudson.util.PersistedList
import jenkins.model.Jenkins
import jenkins.branch.*
import jenkins.plugins.git.*
import org.jenkinsci.plugins.workflow.multibranch.*
import com.cloudbees.hudson.plugins.folder.*
import jenkins.plugins.git.traits.CleanBeforeCheckoutTrait
import jenkins.plugins.git.traits.BranchDiscoveryTrait
import hudson.plugins.git.extensions.impl.CleanBeforeCheckout
import com.cloudbees.hudson.plugins.folder.computed.DefaultOrphanedItemStrategy


def initMultibranchPipelineStr = System.getenv('INIT_MULTIBRANCH_PIPELINE')
List<String> gitRepos = initMultibranchPipelineStr?.split(',')

println "==============新建流水线开始========================"

gitRepos.forEach { gitRepo ->
    try{
        def repoName = gitRepo.substring(gitRepo.lastIndexOf('/') + 1, gitRepo.indexOf('.git'))
        String jobName = "${repoName}" as String
        String jobDescription = "${gitRepo}" as String
        String credentialsId = "gitlab-builder"
        String jobScript = "Jenkinsfile"

        println "==============新建流水线${jobName}========================"

        // Create MultiBranch pipeline
        Jenkins jenkins = Jenkins.instance
        WorkflowMultiBranchProject mbp = jenkins.createProject(WorkflowMultiBranchProject.class, jobName)
        mbp.description = jobDescription
        mbp.getProjectFactory().setScriptPath(jobScript)

        // Add git repo
        String id = UUID.randomUUID().toString()
        String remote = gitRepo
        String includes = "*"
        String excludes = ""
        boolean ignoreOnPushNotifications = false
        GitSCMSource gitSCMSource = new GitSCMSource(id, remote, credentialsId, includes, excludes, ignoreOnPushNotifications)
        BranchSource branchSource = new BranchSource(gitSCMSource)

        //清理配置
        CleanBeforeCheckout cleanCheckout = new CleanBeforeCheckout()
        cleanCheckout.setDeleteUntrackedNestedRepositories(true)
        CleanBeforeCheckoutTrait cleanBeforeCheckoutTrait = new CleanBeforeCheckoutTrait(cleanCheckout)
        BranchDiscoveryTrait branchDiscoveryTrait =  new BranchDiscoveryTrait()

        //设置配置
        gitSCMSource.setTraits([branchDiscoveryTrait,cleanBeforeCheckoutTrait])

        PersistedList sources = mbp.getSourcesList()
        sources.clear()
        sources.add(branchSource)

        //设置过期时间
        mbp.setOrphanedItemStrategy(new DefaultOrphanedItemStrategy(true,30,10))

        // Trigger initial build (scan)
        jenkins.getItem(jobName).scheduleBuild()

        jenkins.save()

        println "==============新建流水线${jobName}成功========================"
    }catch (Exception e){
        println "==============新建流水线${jobName}失败========================"
        println e.getMessage()
    }

}

println "==============新建流水线完成========================"


```