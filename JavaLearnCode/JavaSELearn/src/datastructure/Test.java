package datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Test {
    // top-k 问题 通过堆来解决
    /*
    创建大小为 K 的小根堆
    然后依次遍历 len - k 个元素，
    如果当前元素大于堆顶元素，则替换堆顶元素，并将堆顶元素放到堆底，并向上调整
    最后返回整个堆的元素即可
     */
    //int [] arr = new int[]{100,2,34,45,67,102,23,11,-1,3,108,109,12,33,11,127};
    public static TestHeap topK(int[] arr, int k){
        if (arr == null || arr.length == 0 || k < 0 || k > arr.length)
            throw new IllegalArgumentException();
        int[] topArr = new int[k];
        TestHeap myHeap = new TestHeap(topArr, -1); // 小根堆
        for (int i = k; i < arr.length; i++) {
            if (Integer.compare(arr[i], myHeap.peek()) >= 0){
                myHeap.poll();
                myHeap.offer(arr[i]);
            }
        }
        return myHeap;
    }


    public static void mapTest(){
        // 如果需要使用某一个工具来存放以后会查找的对象，可以使用 map 来存放
        Map<String, Integer> map = new TreeMap<>();
        // 存放一个键值对
        map.put("abc", 123);
        map.put("def", 456);
        map.put("ghi", 789);
        // 打印 map 中的所有键值对
        System.out.println(map);
        // 查找一个 Key
        System.out.println("Key: abc" + map.get("abc"));
        System.out.println("Key: def" + map.get("def"));
        // 删除一个 Key
        map.remove("abc");
        System.out.println(map);
        // 查找一个 Key 是否存在
        System.out.println(map.containsKey("abc"));
        System.out.println(map.containsKey("def"));
        // 查找一个 Value 是否存在
        System.out.println(map.containsValue(123));
        System.out.println(map.containsValue(456));
        // 查找所有的 Key
        System.out.println(map.keySet());
        // 在 Map 中，每一个键值对视为一个 Entry 对象
        // 通过使用 map.entrySet() 方法可以获取到所有的 Entry 对象
        // 然后使用 Set 来存放这些 Entry 对象
        // Set 的本质就是一个 Map，所以存放的所有元素都是一个键值对（Key + 默认的 Object 对象）
        // 在这里 Set 存放的元素类型是 Map.Entry<String, Integer>，也就是 Map 中的 Key
        // 同样可以理解为将 Map 的映射关系存放到了 Set 中
        // 上面是本质性理解
        // 实际在使用时，可以直接将 Set 视为一个集合，是一个不允许存放相同元素的集合，这是因为 Map 的 Key 要唯一
        // 所以当需要存放不同元素时，或者需要过滤重复元素时，可以使用 Set 来存放
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        for (Map.Entry<String, Integer> entry : set) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("********* HashMap *********");
        // HashMap 与 TreeMap 的使用方式相同，因为都实现了 Map 接口
        // 不同点在于其查找的时间复杂度不同，TreeMap 是 logN，HashMap 是 1
        Map<String, Integer> myMap2 = new HashMap<>();
        myMap2.put("abc", 123);
        myMap2.put("def", 456);
        myMap2.put("ghi", 789);
        System.out.println(myMap2);
        Set<String> mySet = myMap2.keySet();
        System.out.println(mySet);
    }

    public static void TestMyHeap(){
        int[] arr = {11,20,4,5,10,8,2,15,6,19};
        TestHeap myHeap = new TestHeap(arr, 1);
        System.out.println(myHeap);
        int ret = myHeap.poll();
        System.out.println("ret: "+ret);
        System.out.println(myHeap);
        myHeap.offer(100);
        System.out.println(myHeap);
    }

    public static void main(String[] args) {
//        mapTest();
//        TestMyHeap();
        int [] arr = new int[]{100,2,34,45,67,102,23,11,-1,3,108,109,12,33,11,127};
        System.out.println(topK(arr,4));
    }
}