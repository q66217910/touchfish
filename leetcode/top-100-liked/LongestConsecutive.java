import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
 * <p>
 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：nums = [100,4,200,1,3,2]
 * 输出：4
 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
 * 示例 2：
 * <p>
 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
 * 输出：9
 * 示例 3：
 * <p>
 * 输入：nums = [1,0,1,2]
 * 输出：3
 */
public class LongestConsecutive {

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int max = 0;
        // 从set中找出不存在num-1的数，从这个数开始向后找，直到不存在num+1的数为止，记录下这个序列的长度，然后继续找下一个不存在num-1的数，重复上述过程，直到set中所有的数都被处理过
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int cur = num;
                int curMax = 1;
                while (set.contains(cur + 1)) {
                    cur++;
                    curMax++;
                }
                max = Math.max(max, curMax);
            }
        }
        return max;
    }

}
