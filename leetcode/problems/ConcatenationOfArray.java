import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcatenationOfArray {

    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] res = new int[2 * n];
        for (int i = 0; i < n; i++) {
            res[i] = nums[i];
            res[i + n] = nums[i];
        }
        return res;
    }

    public int[] shuffle(int[] nums, int n) {
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < nums.length; i++) {
            if (i < n) {
                a[i] = nums[i];
            } else {
                b[i - n] = nums[i];
            }
        }
        int[] res = new int[2 * n];
        for (int i = 0; i < n; i++) {
            res[i * 2] = a[i];
            res[i * 2 + 1] = b[i];
        }
        return res;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int res = 0;
        int count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                count = 0;
            }
            res = Math.max(count, res);
        }
        return res;
    }

    public int[] findErrorNums(int[] nums) {
        int n = nums.length;
        int[] res = new int[2];
        int[] tmp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            tmp[num - 1] = tmp[num - 1] + 1;
        }
        for (int i = 0; i < n; i++) {
            if (tmp[i] == 2) {
                res[0] = i;
            }
            if (tmp[i] == 0) {
                res[1] = i;
            }
        }
        return res;
    }

    public int[] smallerNumbersThanCurrent(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (nums[i] > nums[j]) {
                    res[i] += 1;
                }
            }
        }
        return res;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        int[] tmp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            tmp[num] = tmp[num] + 1;
        }
        for (int i = 1; i <= n; i++) {
            if (tmp[i]==0){
                res.add(i);
            }
        }
        return res;
    }
}
