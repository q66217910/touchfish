package y2024.m08.d13;

/**
 * 如果数组的每一对相邻元素都是两个奇偶性不同的数字，则该数组被认为是一个 特殊数组 。
 * Aging 有一个整数数组 nums。如果 nums 是一个 特殊数组 ，返回 true，否则返回 false。
 * <p>
 * <a href="https://leetcode.cn/problems/special-array-i/">...</a>
 */
public class Solution {
    public boolean isArraySpecial(int[] nums) {
        int last = -1;
        for (int i = 0; i < nums.length; i++) {
            int now = nums[i] % 2;
            if (now == last){
                return false;
            }
            last = now;
        }
        return true;
    }
}
