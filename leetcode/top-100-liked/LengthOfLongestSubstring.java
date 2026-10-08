import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        //滑动窗口
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();//去重
        int res = 0;//结果
        for (int left = 0, right = 0; right < s.length(); right++) {//每一轮右端点都扩一个。
            char ch = chars[right];//right指向的元素，也是当前要考虑的元素
            while (set.contains(ch)) {//set中有ch，则缩短左边界，同时从set集合出元素
                set.remove(chars[left]);
                left++;
            }
            set.add(chars[right]);//别忘。将当前元素加入。
            res = Math.max(res, right - left + 1);//计算当前不重复子串的长度。
        }
        return res;
    }
}
