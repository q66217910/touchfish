package y2024.m09.d04;

import java.util.Collections;
import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/happy-students/">
 * 给你一个下标从 0 开始、长度为 n 的整数数组 nums ，其中 n 是班级中学生的总数。班主任希望能够在让所有学生保持开心的情况下选出一组学生：
 * 如果能够满足下述两个条件之一，则认为第 i 位学生将会保持开心：
 * 这位学生被选中，并且被选中的学生人数 严格大于 nums[i] 。
 * 这位学生没有被选中，并且被选中的学生人数 严格小于 nums[i] 。
 * 返回能够满足让所有学生保持开心的分组方法的数目。
 * </a>
 */
public class Solution {

    /**
     * 根据题意可知，假设数组 nums 的长度为 n，此时设选中学生人数为 k，此时 k∈[0,n]，k 应满足如下：
     * 1. 所有满足 nums[i]<k 的学生应被选中；
     * 2. 所有满足 nums[i]>k 的学生不应被选中；
     * 3. 不能存在 nums[i]=k 的学生；
     */
    public int countWays(List<Integer> nums) {
        int n = nums.size();
        int res = 0;
        Collections.sort(nums);
        for (int k = 0; k <= n; k++) {
            // 前 k 个元素的最大值是否小于 k
            if (k > 0 && nums.get(k - 1) >= k) {
                continue;
            }
            // 后 n - k 个元素的最小值是否大于 k
            if (k < n && nums.get(k) <= k) {
                continue;
            }
            res++;
        }
        return res;
    }

}
