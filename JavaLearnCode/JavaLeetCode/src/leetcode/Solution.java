package leetcode;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int longestConsecutive(int[] nums) {
        // O(n) 的方式创建 Hash 表
        Set<Integer> numSet = new HashSet<>();
        for(int i = 0; i < nums.length; ++i){
            numSet.add(nums[i]);
        }

        int longestStream = 0;
        for (int i : nums){
             // 如果当前 数-1 不在当前的 set 中，则当前这个数可能是某个连续序列的起点
            // 如果当前 数-1 在当前的 set 中，则一定不是连续序列的起点，跳过
            if (!numSet.contains(i - 1)){
                // 统计后续的数字会不会在 set 中
                int curNum = i;
                while(numSet.contains(curNum + 1)){
                    ++curNum;
                }
                int curStream = curNum - i + 1;
                longestStream = Math.max(longestStream, curStream);
            }
        }
        return longestStream;
    }
}
