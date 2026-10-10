import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PartitionEqualSubsetSum {

    public boolean canPartition(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum % 2 > 0) {
            return false;
        }
        //总和应该为target
        int target = sum / 2;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int val = nums[i];
            if (set.contains(target - val) || target ==val) {
                return true;
            }

            new HashSet<>(set).forEach(num -> {
                if (target > num + val) {
                    set.add(num + val);
                }
            });
            set.add(val);
        }
        return false;
    }

}
