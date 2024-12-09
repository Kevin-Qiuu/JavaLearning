public class SolutionTest {

    /*****************************
     专题一：双指针
     *****************************/

    // 1089. 复写零
    // 采用双指针解决此问题
    // 首先明确 cur 与 dest 的位置 先“异地”分析再“就地”分析可以轻松得到调整的方式
    // 然后向前调整
    public static void duplicateZeros(int[] arr) {
        if (arr.length == 0)
            return;
        int cur = 0, dest = 0;
        // 明确 cur 与 dest 的位置
        while(dest < arr.length){
             if(arr[cur] != 0) ++dest;
             else dest += 2;
             if (dest < arr.length) ++cur;
        }
        // 判断是否越界的两种情况
        if(dest == arr.length) --dest;
        else {
            arr[arr.length - 1] = 0;
            dest = arr.length - 2;
            cur--;
        }
        // 开始调整
        while(dest >= 0 && cur >= 0) {
            if (arr[cur] != 0) arr[dest--] = arr[cur--];
            else {
                arr[dest--] = 0;
                arr[dest--] = 0;
                cur--;
            }
        }
    }

    // 移动零
    // 将数组划分成三个区间：处理完毕区间（非零元素区间）、零区间、待处理区间
    // dest：一直指向非零元素区间的最后一个元素
    // 只要还有待处理区间就要一直进行操作
    public static void moveZeroes(int[] nums) {
        if (nums.length == 0)
            return;
        int cur = 0, dest = -1;
        while(cur < nums.length){
            if(nums[cur] != 0) {
                int temp = nums[++dest];
                nums[dest] = nums[cur];
                nums[cur] = temp;
            }
            ++cur;
        }
    }

    // 快乐数
    // 此题关键是一定可以形成环，而快乐树的环只有数字 1 这一个元素。
    // 类似于判断链表是否有环的思想，使用快慢指针判断最终相遇的元素是不是 1。
    public  static int numPower(int n){
        int ret = 0;
        while (n != 0){
            ret += (n % 10) * (n % 10);
            n /= 10;
        }
        return ret;
    }
    public static boolean isHappy(int n) {
        int slow = n, fast = numPower(n);
        while(slow != fast){
            slow = numPower(slow);
            fast = numPower(fast);
            fast = numPower(fast);
        }
        return slow == 1;
    }

    // 11. 盛最多水的容器
    // 对暴力算法进行优化，暴力一定会超时的
    // 使用两个指针控制遍历的范围即当前水箱的宽度
    // v = h * w 首先使得 w 最大，然后缩小 w 的范围，如果此时 h 也变小，则可以进一步把 w 再缩小
    public int maxArea(int[] height) {
        if(height.length == 0) return 0;
        int left = 0, right = height.length;
        int max_v = 0;
    }

    public static void main(String[] args) {
//        System.out.println("hello world");
//        int[] arr = new int[]{1, 0 ,2, 3, 0, 4, 5, 0};
//        int[] arr = {1, 0 ,2, 3, 0, 0};
//        SolutionTest.duplicateZeros(arr);

//        int[] arr = {0, 1, 0, 3, 0, 12};
//        int[] arr = {0, 0, 0, 0, 0, 12};
//        SolutionTest.moveZeroes(arr);
        System.out.println(isHappy(2));
        System.out.println();
    }
}
