package datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.swap;

public class TestHeap {
    /*
    已知孩子节点 i 求父亲节点 parent，在顺序表存储的二叉树中
    parent = (i - 1) / 2
     */

    /*
    已知父亲节点 i 求孩子节点 left 和 right，顺序表存储的二叉树中
    left = 2 * i + 1
    right = 2 * i + 2
     */

    private int[] elem;
    private int usedSize ;
    private int flag; // 1 表示大根堆，-1 表示小根堆

    public TestHeap(int[] elem, int flag) {
        initHeap(elem, flag);
        createHeap();
    }

    private void initHeap(int[] elem, int flag){
        this.elem = elem;
        this.usedSize = elem.length;
        this.flag = flag;
    }

    /*
    创建堆，对最小的一棵子树的根节点向下调整，依次遍历所有子树的根节点创建堆，

     */

    private void createHeap(){
        int parentIndex = (usedSize - 1 - 1) / 2;
        for (int i = parentIndex; i >= 0; i--){
            siftDown(i, usedSize);
        }
    }

    // 向下调整
    /*
    比较根节点与其左右孩子节点的大小，若根节点大于左右孩子节点，则不需要调整，
    否则将根节点与左右孩子节点中最小的节点交换
     */
    public void siftDown(int parent, int usedSize){
        int left_child = parent*2 + 1;
        int right_child = parent*2 + 2;
        while (left_child < usedSize){
            int tempChild = left_child;
            // 表示有左孩子节点
            if (right_child < usedSize){
                // 表示有右孩子节点
                // key：左右孩子
                // value：左右孩子的值
                Map.Entry<Integer, Integer> child = findMax(left_child, right_child);
                if (Integer.compare(elem[parent], child.getValue()) * flag < 0){
                    swap(elem, parent, child.getKey());
                    tempChild = child.getKey();
                }
            } else {
                // 没有右孩子节点
                if (Integer.compare(elem[parent], elem[left_child]) * flag < 0){
                    swap(elem, left_child, parent);
                }
            }
            parent = tempChild;
            left_child = parent * 2 + 1;
            right_child = parent * 2 + 2;
        }
    }

    private void swap(int[] elem, int left, int right){
        int temp = elem[left];
        elem[left] = elem[right];
        elem[right] = temp;
    }


    private Map.Entry<Integer, Integer> findMax(int leftChild, int rightChild) {
        Map<Integer, Integer> map = new HashMap<>();
        if (Integer.compare(elem[leftChild], elem[rightChild]) * flag >= 0)
            map.put(leftChild, elem[leftChild]);
        else
            map.put(rightChild, elem[rightChild]);
        return map.entrySet().iterator().next();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elem.length; i++) {
            sb.append(elem[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    // 向上调整
    public void siftUp(int child, int usedSize){
        int parent = (child - 1) / 2;
        while (parent >= 0 && elem[parent] < elem[child]){
            if (Integer.compare(elem[parent], elem[child]) * flag < 0){
                swap(elem, parent, child);
            }
            child = parent;
            parent = (child - 1) / 2;
        }
    }

    // 入堆
    public void offer(int val){
        if (usedSize == elem.length){
            elem = Arrays.copyOf(elem, elem.length + 5);
        }
        elem[usedSize++] = val;
        siftUp(usedSize - 1, usedSize);
    }

    // 出堆
    public int poll(){
        int top = elem[0];
        elem[0] = elem[--usedSize];
        siftDown(0, usedSize);
        return top;
    }

    // 瞄一眼堆顶元素
    // 不会弹出，只看一眼
    public int peek(){
        return elem[0];
    }

    // 判断是否为空
    private boolean isEmpty(){
        return usedSize == 0;
    }

    // 判断是否满
    private boolean isFull(){
        return usedSize == elem.length;
    }



}
