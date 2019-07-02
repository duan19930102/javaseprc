package String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class StringTest {
    public static String a = "   abc  ";
    public static void main(String[] args) {
//        String a = "a"+"b"+1;
//        String b = "ab1";
//        System.err.println(a==b);
//
//        String a1 = new String("ab1");
//        System.err.println(a==a1);

        trimTest();
        a.substring(0,2);

       // ArrayList
       // LinkedList





    }

    public static String trimTest(){
        char[] val = a.toCharArray();
        int len = val.length;
        int st = 0;

        //找到没有空白字符的开始位置
        while ((st<len) && val[st]<=' '){
            st++;
        }

        while ((st<len) && val[len-1]<=' ') {
            len--;
        }
        return ((st > 0) || (len < val.length)) ? a.substring(st, len) : a;
    }
}
