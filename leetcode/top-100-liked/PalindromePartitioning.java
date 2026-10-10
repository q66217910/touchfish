import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PalindromePartitioning {

    List<List<String>> res = new ArrayList<>();

    List<String> ans = new ArrayList<String>();

    boolean[][] dp;

    public List<List<String>> partition(String s) {

        int n = s.length();
        //从i到j是否是回文串
        dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], true);
        }
        //判断所有的回文串
        for (int i = n - 1; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
            }
        }
        //深度遍历算法
        dfs(s, 0);
        return res;
    }


    private void dfs(String s, int i) {
        int n = s.length();
        if (i == n) {
            res.add(new ArrayList<>(ans));
            return;
        }
        for (int j = i; j < n; ++j) {
            if (dp[i][j]){
                ans.add(s.substring(i, j + 1));
                dfs(s, j + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }
}
