import java.util.ArrayList;
import java.util.List;

public class Stack {

    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        int m = target.length;
        int j = 0;
        for (int i = 1; i <= n; i++) {
            res.add("Push");
            if (j < m && target[j] > i) {
                res.add("Pop");
            } else if (j >= m-1) {
                break;
            } else {
                j++;
            }
        }
        return res;
    }

    public int evalRPN(String[] tokens) {
      return 0;
    }
}
