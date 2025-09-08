package learnSE.string_func;

import java.util.Scanner;

public class Test {

    public static boolean isNumberOrChar(char ch){
        return ch < 'a' || ch > 'z';
    }

    public static boolean isPalindrome(String str){
        str = str.toLowerCase();
        /*
        for (int i = 0; i < str.length()/2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1))
                return false;
        }
        return true;
         */
        // 一个新思路 给两个索引变量，判断是否会相遇
        for (int i= 0, j = str.length()-1; i < j;){
            if (isNumberOrChar(str.charAt(i))){
                ++i;
                continue;
            }
            if (isNumberOrChar(str.charAt(j))){
                --j;
                continue;
            }
            if (str.charAt(i) != str.charAt(j))
                return false;
            else{
                ++i;
                --j;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(new Scanner(System.in).nextLine()));
    }

    public static int lastStrLen1(String str){
        String[] strs = str.split(" ");
        return strs[strs.length-1].length();
    }

    public static int lastStrLen(String str){
        int tarPos = str.lastIndexOf(' ') + 1;
        return str.substring(tarPos).length();
    }

    public static void main1(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println(lastStrLen(input));
    }
}
