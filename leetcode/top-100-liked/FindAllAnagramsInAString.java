import java.util.ArrayList;
import java.util.List;

public class FindAllAnagramsInAString {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        char[] chars = s.toCharArray();
        int n = p.length();
        for (int left = 0, right = n - 1; right <= s.length(); left++, right++) {
            //判断是否是异位词
            if (isAnagram(chars, left, right, p)) {
                res.add(left);
            }
        }
        return res;
    }

    private boolean isAnagram(char[] chars, int left, int right, String p) {
        //判断是否是异位词
        int[] charCount = new int[26];
        for (int i = left; i < right; i++) {
            charCount[chars[i] - 'a']++;
            charCount[p.charAt(i - left) - 'a']--;
        }
        for (int count : charCount) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }
}
