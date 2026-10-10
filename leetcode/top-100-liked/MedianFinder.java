import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {

    // 最大堆，存放较小的一半元素
    private PriorityQueue<Integer> small;
    // 最小堆，存放较大的一半元素
    private PriorityQueue<Integer> large;

    public MedianFinder() {
        small = new PriorityQueue<>(Collections.reverseOrder());
        large = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // 先加入最大堆
        small.offer(num);
        // 将最大堆中最大的元素移到最小堆，保证 small 中所有元素 <= large 中所有元素
        large.offer(small.poll());
        // 平衡两个堆的大小：保持 small.size() == large.size() 或 small.size() == large.size() + 1
        if (small.size() < large.size()) {
            small.offer(large.poll());
        }
    }

    public double findMedian() {
        // 如果 small 比 large 多一个元素，中位数就是 small 的堆顶
        if (small.size() > large.size()) {
            return small.peek();
        }
        // 否则两个堆大小相等，中位数是两个堆顶的平均值
        return (small.peek() + large.peek()) / 2.0;
    }

}
