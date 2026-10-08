public class TrappingRainWater {

    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }
        // leftMax表示第i位的接水量
        int[] leftMax = new int[n];
        //遍历获取正向的接水量
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            //状态转移方程，
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        //遍历获取正向的接水量
        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            //状态转移方程，
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        //正反方向取最小
        int max = 0;
        for (int i = 0; i < n; i++) {
            max += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return max;
    }

}
