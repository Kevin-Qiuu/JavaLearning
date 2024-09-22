package homework;

import java.util.ArrayList;

public class Bit_array {

    /*
    实现一个方法 transform, 以数组为参数, 循环将数组中的每个元素 乘以 2 ,
    并设置到对应的数组元素上. 例如 原数组为 {1, 2, 3}, 修改之后为 {2, 4, 6}
     */
    public static void print_arr(int[] arr){
        for (int x : arr)
            System.out.print(x + " ");
    }
    public static void transform(int[] arr){
        for (int i = 0; i < arr.length; ++i)
            arr[i] *= 2;
    }

    public static void main1(String[] args) {
//        int[] arr = new int[]{1, 2, 3};
//        transform(arr);
//        print_arr(arr);
        int[] arr = new int[]{1, 11, 3, 4, 15, 6, 7, 8, 8, 3};
//        adj_arr_1(arr);
//        bubble_sort(arr);
        int[] ret = find_tar_i(arr, 9);
        print_arr(ret);
    }
    /*
    调整数组顺序使得奇数位于偶数之前。调整之后，不关心大小顺序。
    如数组：[1,2,3,4,5,6]
    调整后可能是：[1, 5, 3, 4, 2, 6]
     */
    public static void adj_arr(int[] arr){
        ArrayList<Integer> arr_1 = new ArrayList<>();
        ArrayList<Integer> arr_2 = new ArrayList<>();
        for (int j : arr) {
            if (j % 2 == 0)
                arr_1.add(j);
            else
                arr_2.add(j);
        }
        for(int i = 0; i < arr_2.size(); ++i){
            arr[i] = arr_2.get(i);
        }
        for(int i = 0; i < arr_1.size(); ++i){
            arr[i + arr_2.size()] = arr_1.get(i);
        }
    }

    public static int find_odd(int[] arr, int eve_i){
        for (int i = eve_i; i < arr.length; i++) {
            if (arr[i] % 2 != 0)
                return i;
        }
        return -1;
    }

    public static void adj_arr_1(int[] arr){
        int next_odd_i = 0;
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] % 2 == 0 ){
                // 往后面找奇数
                int ret_i = find_odd(arr, i);
                if (ret_i == -1)
                    return;
                else{
                    // 交换
                    int temp = arr[i];
                    arr[i] = arr[ret_i];
                    arr[ret_i] = temp;
                }
            }
        }

    }


    /*
    给定一个整数数组 nums 和一个整数目标值 target，
    请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
    你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    你可以按任意顺序返回答案。
    示例 1：
    输入：nums = [2,7,11,15], target = 9
    输出：[0,1]
    解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     */
    
    public static int[] find_tar_i(int[] arr, int target){
        int[] ret_arr = new int[]{-1, -1};
//        bubble_sort(arr);
//        // 找到已经大于目标值的下标
//        int big_tar_i = arr.length- 1;
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] >= target){
//                big_tar_i = i;
//                break;
//            }
//
//        }
        // 一个一个遍历
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++){
                if (arr[i] + arr[j] == target){
                    ret_arr[0] = i;
                    ret_arr[1] = j;
                }
            }
        }
        return ret_arr;
    }

    /*
    给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
    输入: [2,2,1]
    输出:

    输入: [4,1,2,1,2]
    输出: 4
     */
    public static int find_once(int[] arr){
        int ret = 0;
        for (int x : arr){
            ret = ret ^ x;
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,2,1,3,4,5,4};
        System.out.println(find_once(arr));
    }

    /*
    给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
    你可以假设数组是非空的，并且给定的数组总是存在多数元素。
    输入：[3,2,3]
    输出：3

    输入：[2,2,1,1,1,2,2]
    输出：2
     */

    /*
    给你一个整数数组 arr，请你判断数组中是否存在连续三个元素都是奇数的情况：如果存在，请返回 true ；否则，返回 false 。

    示例 1：
    输入：arr = [2,6,4,1]
    输出：false
    解释：不存在连续三个元素都是奇数的情况。

    示例 2：
    输入：arr = [1,2,34,3,4,5,7,23,12]
    输出：true
    解释：存在连续三个元素都是奇数的情况，即 [5,7,23] 。

     */

    /*
    给定一个整型数组, 实现冒泡排序(升序排序)
     */
    public static void bubble_sort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++){
                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
    /*
    给定一个有序整型数组, 实现二分查找
     */
}
