package y2024.m08.d18;

/**
 * <a href="https://leetcode.cn/problems/student-attendance-record-i/">
 * 给你一个字符串 s 表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
 * 'A'：Absent，缺勤
 * 'L'：Late，迟到
 * 'P'：Present，到场
 * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
 * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
 * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
 * 如果学生可以获得出勤奖励，返回 true ；否则，返回 false 。
 * </a>
 */
public class Solution {

    public boolean checkRecord(String s) {
        int aNum = 0;
        int lnum = 0;
        for (char c : s.toCharArray()){
            if (c == 'A'){
               aNum++;
                lnum=0;
               if (aNum>=2){
                   return false;
               }
            }else if (c == 'L'){
                lnum++;
                if (lnum>=3){
                    return false;
                }
            }else {
                lnum=0;
            }
        }
        return true;
    }

}
