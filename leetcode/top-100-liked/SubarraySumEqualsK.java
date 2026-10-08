import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SubarraySumEqualsK {

    public int subarraySum(int[] nums, int k) {
        //存储差值达到K
        Set<Integer> set = new HashSet<>();
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(k - nums[i]) || k - nums[i] == 0) {
                res++;
            }
            for (Integer a : set) {
                if (k - a - nums[i] > 0) {
                    set.add(a + nums[i]);
                }
            }
            if (k - nums[i] > 0) {
                set.add(nums[i]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        SubarraySumEqualsK subarraySumEqualsK = new SubarraySumEqualsK();
        int[] nums = {-1, -1, 1};
        int k = 1;
        System.out.println(subarraySumEqualsK.subarraySum(nums, k));
    }
}
