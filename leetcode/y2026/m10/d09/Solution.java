package m10.d09;

public class Solution {

    public int minInsertions(String s) {

        int res = 0;
        int need = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                need += 2;
                if (need % 2 == 1) {  //右括号数量只能为偶数，但只能添（右括号少了）
                    res++;  //插入一个右括号
                    need--; //对右括号的需求-1
                }
            }else {
                need--;
                //need不可能小于-1，因为此处-1后若走上面必有+2
                if (need == -1) {  //右括号多了
                    res++;  //插入一个左括号
                    need = 1;
                }
            }
        }
        return res + need;

    }

}
