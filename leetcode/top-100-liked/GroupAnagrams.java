import java.util.*;

/**
 * Group Anagrams   字母异位词分组
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
 * <p>
 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * <p>
 * 解释：
 * <p>
 * 在 strs 中没有字符串可以通过重新排列来形成 "bat"。
 * 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 字符串 "ate" ，"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此。
 * 示例 2:
 * <p>
 * 输入: strs = [""]
 * <p>
 * 输出: [[""]]
 * <p>
 * 示例 3:
 * <p>
 * 输入: strs = ["a"]
 * <p>
 * 输出: [["a"]]
 */
class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            // 将字符串转换为字符数组
            char[] chars = str.toCharArray();
            // 对字符数组进行排序
            Arrays.sort(chars);
            String value = new String(chars);
            if (map.containsKey(value)) {
                map.get(value).add(str);
            } else {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(value, list);
            }
        }
        return new ArrayList<>(map.values());
    }

}