package y2024.m08.d24;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="https://leetcode.cn/problems/permutation-difference-between-two-strings/description/">
 * ���������ַ��� s �� t��ÿ���ַ����е��ַ������ظ����� t �� s ��һ�����С�
 * ���в� ����Ϊ s �� t ��ÿ���ַ��������ַ�����λ�õľ��Բ�ֵ֮�͡�
 * ���� s �� t ֮��� ���в� ��
 * </a>
 *
 */
public class Solution {

    public int findPermutationDifference(String s, String t) {
        Map<Character, Integer> char2index = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            char2index.put(s.charAt(i), i);
        }
        int sum = 0;
        for (int i = 0; i < t.length(); ++i) {
            sum += Math.abs(i - char2index.get(t.charAt(i)));
        }
        return sum;
    }

}
