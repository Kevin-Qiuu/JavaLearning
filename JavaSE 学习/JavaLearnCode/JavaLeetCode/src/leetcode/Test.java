package leetcode;

import com.sun.source.tree.Tree;

import java.util.*;

public class Test {
    public static void SolutionLongestConsecutive(){
        int[] nums = {1, 2, 5, 6, 4, 100, 3};
        Solution sl = new Solution();
        int ret = sl.longestConsecutive(nums);
        System.out.println((ret));
    }

    public static <T> void SolutionZhiTraverse(TreeNode<T> root){
        // 奇数层：左到右
        // 偶数层：右到左
        // 使用一个栈和一个队列
        Stack<TreeNode<T>> nodeStack = new Stack<>();
        Queue<TreeNode<T>> nodeQueue = new ArrayDeque<>();
        int flag = 1;
        nodeQueue.offer(root);
        while(!nodeQueue.isEmpty()){
            while(!nodeQueue.isEmpty()){
                TreeNode<T> temp = nodeQueue.poll();
                System.out.print(temp.val + " ");
                nodeStack.push(temp);
            }
            while(!nodeStack.isEmpty()){
                TreeNode<T> temp = nodeStack.pop();
                if(flag == 1){
                    if(temp.rChild != null) nodeQueue.offer(temp.rChild);
                    if(temp.lChild != null) nodeQueue.offer(temp.lChild);
                } else {
                    if(temp.lChild != null) nodeQueue.offer(temp.lChild);
                    if(temp.rChild != null) nodeQueue.offer(temp.rChild);
                }
            }
            flag *= -1;
        }

    }

    @org.jetbrains.annotations.Nullable
    public static <T> TreeNode<T> CreateBtInSequentialTraverse(ArrayList<T> values, T nullFlag){
        // 调用这个方法需要给出完整的层序遍历，注意空字符也应给出
        // 根据层序遍历创建二叉树
        // 判断根节点是否为空
        if(values.isEmpty() || values.get(0) == nullFlag)
            return null;
        // 1. 创建一个队列
        Queue<TreeNode<T>> nodeQueue = new LinkedList<>();
        // 2. 创建一个根节点并将其入队列
        TreeNode<T> root = new TreeNode<>(values.get(0));
        nodeQueue.offer(root);
        int index = 0;
        while(index < values.size() && !nodeQueue.isEmpty()){
            TreeNode<T> temp = nodeQueue.poll();
            TreeNode<T> lChild = null;
            TreeNode<T> rChild = null;
            // 如果长度小于 values 并且不是空标志则创建，否则跳过
            if (index * 2 + 1 < values.size() && !values.get(index * 2 + 1).equals(nullFlag)) {
                lChild = new TreeNode<>(values.get(index * 2 + 1));
                temp.lChild = lChild;
                nodeQueue.offer(lChild);
            }
            if (index * 2 + 2 < values.size() && !values.get(index * 2 + 2).equals(nullFlag)) {
                rChild = new TreeNode<>(values.get(index * 2 + 2));
                temp.rChild = rChild;
                nodeQueue.offer(temp.rChild);
            }
            while ((++index) < values.size() && values.get(index) == nullFlag);
        }
        return root;
    }
    public static void main(String[] args) {
//        SolutionLongestConsecutive();
        // 给定一颗二叉树，对这颗二叉树进行之字形遍历
        // 假设存在下方这个二叉树序列
        // 1 2 3 4 5 6 7
        ArrayList<String> nodeValues = new ArrayList<>();
        nodeValues.add("1");
        nodeValues.add("2");
        nodeValues.add("3");
        nodeValues.add("4");
        nodeValues.add("5");
        nodeValues.add("6");
        nodeValues.add("#");
        nodeValues.add("#");
        nodeValues.add("7");
        nodeValues.add("8");
        nodeValues.add("#");
        nodeValues.add("#");
        nodeValues.add("9");

        TreeNode<String> root = CreateBtInSequentialTraverse(nodeValues, "#");
//        System.out.println(root);
        SolutionZhiTraverse(root);
    }
}
