public class LongestCommonSubsequence {

    public int longestCommonSubsequence(String text1, String text2) {
        int n = text1.length();
        int m = text2.length();
        //dp表示text1[0,i]和text2[0,j]最长公共子序列的长度
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            char c1 = text1.charAt(i-1);
            for (int j = 1; j <= m; j++) {
                char c2 = text2.charAt(j-1);
                if (c1 == c2) {
                    //字符相同时,看dp[i - 1][j - 1]的最长长度
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][m];
    }

}
