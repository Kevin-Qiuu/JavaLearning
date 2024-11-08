package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}




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

    public int singleNumber(int[] nums) {
        // 方法一 采用异或
//        int ret = 0;
//        for (int i = 0; i < nums.length; ++i){
//            ret ^= nums[i];
//        }
//        return ret;
        // 方法二 采用 set
        // 判断当前元素是否已经在 set 中，在的话就删除
        // 最后剩下的就是那个不重复的元素
        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; ++i){
            if (numSet.contains(nums[i])){
                numSet.remove(nums[i]);
            } else {
                numSet.add(nums[i]);
            }
        }
        return numSet.iterator().next();
    }

    public Node copyRandomList(Node head) {
        if (head == null)
            return null;

        Node newHead = new Node(head.val);
        Node curOld = head;
        Node curNew = newHead;
        Map<Node, Node> nodeMap = new HashMap<>();
        nodeMap.put(curOld, curNew);

        // 拷贝链表以及制作 nodemap
        while(curOld.next != null){
            curNew.next = new Node(curOld.next.val);
            curNew = curNew.next;
            curOld = curOld.next;
            nodeMap.put(curOld, curNew);
        }

        // 拷贝 random
        curOld = head;
        curNew = newHead;
        while(curOld != null){
            curNew.random = nodeMap.get(curOld.random);
            curNew = curNew.next;
            curOld = curOld.next;
        }

        return newHead;
    }

    public int numJewelsInStones(String jewels, String stones) {
        int stoneNum = 0;
        Set<String> jewelSet = new HashSet<>();
        for(int i = 0; i < jewels.length(); ++i){
            jewelSet.add(String.valueOf(jewels.getBytes()[i]));
        }
        for (int i = 0; i < stones.length(); i++) {
            if(jewelSet.contains(String.valueOf(stones.getBytes()[i])))
                stoneNum++;
        }
        return stoneNum;
    }
}


