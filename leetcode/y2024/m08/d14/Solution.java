package y2024.m08.d14;

import java.util.Arrays;

/**
 * 如果数组的每一对相邻元素都是两个奇偶性不同的数字，则该数组被认为是一个 特殊数组 。
 * <p>
 * 周洋哥有一个整数数组 nums 和一个二维整数矩阵 queries，对于 queries[i] = [fromi, toi]，请你帮助周洋哥检查子数组 nums[fromi..toi] 是不是一个 特殊数组 。
 * <p>
 * 返回布尔数组 answer，如果 nums[fromi..toi] 是特殊数组，则 answer[i] 为 true ，否则，answer[i] 为 false 。
 * <p>
 * <a href="https://leetcode.cn/problems/special-array-ii/description/">...</a>
 */
public class Solution {

    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            if (((nums[i] ^ nums[i - 1]) & 1) != 0) {
                dp[i] = dp[i - 1] + 1;
            }
        }

        boolean[] res = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0], y = queries[i][1];
            res[i] = dp[y] >= y - x + 1;
        }
        return res;
    }

}
