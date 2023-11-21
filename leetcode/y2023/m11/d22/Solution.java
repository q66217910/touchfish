package y2023.m11.d22;

/*
 *
 * 美化数组的最少删除数
 * https://leetcode.cn/problems/minimum-deletions-to-make-array-beautiful/
 *
 */
public class Solution {

    public int minDeletion(int[] nums) {
        int n = nums.length;
        int result = 0;
        boolean check = true;
        for (int i = 0; i + 1 < n; ++i) {
            if (nums[i] == nums[i + 1] && check) {
                result++;
            } else {
                check = !check;
            }
        }
        if ((n - result) % 2 != 0) {
            ++result;
        }
        return result;
    }

}
