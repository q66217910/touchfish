package y2024.m08.d19;

/**
 * <a href="https://leetcode.cn/problems/student-attendance-record-ii/">
 * 可以用字符串表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
 * 'A'：Absent，缺勤
 * 'L'：Late，迟到
 * 'P'：Present，到场
 * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
 * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
 * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
 * 给你一个整数 n ，表示出勤记录的长度（次数）。请你返回记录长度为 n 时，可能获得出勤奖励的记录情况 数量 。答案可能很大，所以返回对 109 + 7 取余 的结果。
 * </a>
 */
public class Solution {

    public int checkRecord(int n) {
        final int MOD = 1000000007;
        int[][] dp = new int[2][3]; // A 的数量，结尾连续 L 的数量
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            int[][] dpNew = new int[2][3];
            // 以 P 结尾的数量
            for (int j = 0; j <= 1; j++) {
                for (int k = 0; k <= 2; k++) {
                    dpNew[j][0] = (dpNew[j][0] + dp[j][k]) % MOD;
                }
            }
            // 以 A 结尾的数量
            for (int k = 0; k <= 2; k++) {
                dpNew[1][0] = (dpNew[1][0] + dp[0][k]) % MOD;
            }
            // 以 L 结尾的数量
            for (int j = 0; j <= 1; j++) {
                for (int k = 1; k <= 2; k++) {
                    dpNew[j][k] = (dpNew[j][k] + dp[j][k - 1]) % MOD;
                }
            }
            dp = dpNew;
        }
        int sum = 0;
        for (int j = 0; j <= 1; j++) {
            for (int k = 0; k <= 2; k++) {
                sum = (sum + dp[j][k]) % MOD;
            }
        }
        return sum;
    }

}
