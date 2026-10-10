import java.util.*;

public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        //对candidates升序排序，以方便根据remain的大小使用return减小搜索空间；
        Arrays.sort(candidates);
        //递归求可能的组合。具体的，每次递归时对所有candidates做一次遍历，情况有3：
        // 1，满足条件，则答案加入一条
        // 2，不足，继续递归，
        // 3，超出，则直接退出本路线。
        combinationFind(candidates, 0, new ArrayList<>(), target, res);
        return res;
    }

    private void combinationFind(int[] candidates, int s, List<Integer> use, int remain, List<List<Integer>> res) {
        //注意每层递归都对全体candidates做遍历会导致如[2,2,3],[3,2,2]这样的对称重复的答案的产生。
        //这是因为发生了“往前选择”的情况，我们每次更深层的递归都往后缩小一个candidates，强制函数只能“往后选择”，这将不会出现重复答案。
        for (int i = s; i < candidates.length; i++) {
            int candidate = candidates[i];
            if (candidate == remain) {
                ArrayList<Integer> list = new ArrayList<>(use);
                list.add(candidate);
                res.add(list);
            } else if (candidate < remain) {
                ArrayList<Integer> list = new ArrayList<>(use);
                list.add(candidate);
                combinationFind(candidates, i, list, remain - candidate, res);
            } else {
                return;
            }
        }
    }
}
