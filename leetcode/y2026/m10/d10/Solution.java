package m10.d10;

import java.util.Arrays;

public class Solution {

    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int maxDiff = 0;
        int[] diff = new int[n];

        for (int i = 0; i < n; i++) {
            // 原题 nums1/nums2 非负，这里直接算绝对差即可
            int d = Math.abs(nums1[i] - nums2[i]);
            diff[i] = d;
            maxDiff = Math.max(maxDiff, d);
        }

        // cnt[d] 表示差值为 d 的位置有多少个
        int[] cnt = new int[maxDiff + 1];
        for (int d : diff) {
            cnt[d]++;
        }

        long rem = (long) k1 + k2;

        // 从最大差值开始往下批量降低
        for (int d = maxDiff; d > 0 && rem > 0; d--) {
            if (cnt[d] == 0) continue;

            // 把 cnt[d] 个差值为 d 的元素都降到 d-1，需要 cnt[d] 次操作
            long need = cnt[d];

            if (rem >= need) {
                rem -= need;
                cnt[d - 1] += cnt[d];
                cnt[d] = 0;
            } else {
                // 只能降低 rem 个
                cnt[d - 1] += (int) rem;
                cnt[d] -= (int) rem;
                rem = 0;
            }
        }

        long ans = 0;
        for (int d = 0; d <= maxDiff; d++) {
            if (cnt[d] > 0) {
                ans += (long) d * d * cnt[d];
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().minSumSquareDiff(new int[]{10,10,10,11,5}, new int[]{1,0,6,6,1}, 11, 27));
    }
}
