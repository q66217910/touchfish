package y2024.m09.d15;

import java.util.List;

/**
 * <a href="https://leetcode.cn/problems/points-that-intersect-with-cars/">
 * 给你一个下标从 0 开始的二维整数数组 nums 表示汽车停放在数轴上的坐标。对于任意下标 i，nums[i] = [starti, endi] ，其中 starti 是第 i 辆车的起点，endi 是第 i 辆车的终点。
 * 返回数轴上被车 任意部分 覆盖的整数点的数目。
 * </a>
 */
public class Solution {

    public int numberOfPoints(List<List<Integer>> nums) {
        int C = 0;
        for (List<Integer> interval : nums) {
            C = Math.max(C, interval.get(1));
        }

        int[] count = new int[C + 1];
        for (List<Integer> interval : nums) {
            for (int i = interval.get(0); i <= interval.get(1); ++i) {
                ++count[i];
            }
        }

        int ans = 0;
        for (int i = 1; i <= C; ++i) {
            if (count[i] > 0) {
                ++ans;
            }
        }
        return ans;
    }

}
