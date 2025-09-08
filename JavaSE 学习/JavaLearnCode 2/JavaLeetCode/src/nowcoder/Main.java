package nowcoder;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main1(String[] args) {
        String temp = "1234";
        System.out.println(String.valueOf(temp.charAt(0)));
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String target = in.next();
        String result = in.next();
        StringBuilder brokenKeys = brokenKeyboard(target, result);
        System.out.println(brokenKeys);
    }

    public static StringBuilder brokenKeyboard(String target, String result){
        Set<String> keySet = new HashSet<>();
        Set<String> brokenKeySet = new HashSet<>();
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < result.length(); ++i){
            keySet.add(String.valueOf(result.charAt(i)));
        }
        for(int i = 0; i < target.length(); ++i){
            String curStr = String.valueOf(target.charAt(i));
            if(!keySet.contains(curStr) && !brokenKeySet.contains(curStr.toUpperCase())){
                brokenKeySet.add(curStr.toUpperCase());
                ret.append(curStr.toUpperCase());
            }
        }
        return ret;
    }
}
