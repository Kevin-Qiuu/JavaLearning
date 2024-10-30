package leetcode;

public class Test {
    public static void SolutionLongestConsecutive(){
        int[] nums = {1, 2, 5, 6, 4, 100, 3};
        Solution sl = new Solution();
        int ret = sl.longestConsecutive(nums);
        System.out.println((ret));
    }
    public static void main(String[] args) {
        SolutionLongestConsecutive();
    }
}
