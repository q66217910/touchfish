```
这个结构支持 Markdown 语法, 也支持 YAML 语法, 
甚至纯文本手动敲空格和回车都可以. 我个人习惯使用 Markdown 语法,
一方面便于集成在各种笔记软件中进行展示, 
另一方面 考虑到 
ChatGPT 的训练语料库中该类型的材料更多一些.
```

结构中的信息, 可以根据自己需要进行增减, 从中总结的常用模块包括:

* Role: <name> : 指定角色会让 GPT 聚焦在对应领域进行信息输出

* Profile author/version/description : Credit 和 迭代版本记录

* Goals: 一句话描述 Prompt 目标, 让 GPT Attention 聚焦起来

* Constrains: 描述限制条件, 其实是在帮 GPT 进行剪枝, 减少不必要分支的计算

* Skills: 描述技能项, 强化对应领域的信息权重

* Workflow: 重点中的重点, 你希望 Prompt 按什么方式来对话和输出

* Examples: 在该结构块举 1-3 个示例, 从而进一步提升 Prompt 带来的输出结果提升.

* Initialization: 冷启动时的对白, 也是一个强调需注意重点的机会