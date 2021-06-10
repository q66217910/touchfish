---
layout: post
title: 【leetcode】零钱兑换 II
category: dp
tags: [leetcode]
no-post-nav: true
---



#### [零钱兑换 II](https://leetcode-cn.com/problems/coin-change-2/)

 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。  



示例 1:

```
输入: amount = 5, coins = [1, 2, 5]
输出: 4
解释: 有四种方式可以凑成总金额:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1
```

示例 2:

```
输入: amount = 3, coins = [2]
输出: 0
解释: 只用面额2的硬币不能凑成总金额3。
示例 3:

输入: amount = 10, coins = [10] 
输出: 1
```


注意:

你可以假设：

- 0 <= amount (总金额) <= 5000
- 1 <= coin (硬币面额) <= 5000
- 硬币种类不超过 500 种
- 结果符合 32 位符号整数





### 题解

```
定义dp数组，表示金额之和等于i的硬币组合数
状态转移方式：
	 dp[i] += dp[i - coin];
```



```java
public int change(int amount, int[] coins) {
        //定义dp数组，表示金额之和等于 xx 的硬币组合数
        int[] dp = new int[amount + 1];
        //金额之和为0，有1种就是不取
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                //能组成金额和为i的组合为，能组成去除coin组合数
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }
```

