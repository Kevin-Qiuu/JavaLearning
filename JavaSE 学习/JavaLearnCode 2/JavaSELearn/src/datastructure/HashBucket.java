package datastructure;

public class HashBucket {
    class Node implements Cloneable{
        private int key;
        private int value;
        private Node next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
            next = null;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new Node(key, value);
        }
    }

    private int usedSize;
    private Node[] arr;
    private final float LOAD_FACTOR = 0.75f;

    HashBucket(){
        arr = new Node[5];
        usedSize = 0;
    }

    public void put(int key, int val){
        Node node = new Node(key, val);
        int index = key % arr.length; // Hash 函数
        Node cur = arr[index];
        if (arr[index] == null)
            arr[index] = node;
        else{
            // 采用头插法
            Node temp = arr[index].getNext();
            arr[index].setNext(node);
            node.setNext(temp);
        }
        ++usedSize;

        // 检查是否进行扩容
        if (doLoadFactor() > LOAD_FACTOR){
            Resize();
        }
    }

    public int find(int key){
        int index = key % arr.length;
        Node cur = arr[index];
        while(cur != null){
            if (cur.getKey() == key)
                return cur.getValue();
            cur = cur.getNext();
        }
        return -1;
    }

    // 这一个方法需要理解的是 Java 中除了基础类型变量，其他都是引用变量
    // 而引用变量直接操作的
    private void Resize() {
        Node[] newArr = new Node[arr.length * 2];
        // 重新二次哈希
        for (int i = 0; i < arr.length; ++i){
            Node cur = arr[i];
            while(cur != null){
                int newIndex = cur.getKey() % newArr.length;
                Node curNext = cur.getNext();
                if (newArr[newIndex] != null){
                    // 在新的哈希桶中进行头插
                    Node temp = newArr[newIndex].getNext();
                    newArr[newIndex].setNext(cur);
                    cur.setNext(temp);
                }
                else{
                    newArr[newIndex] = cur;
                }
                cur = curNext;
            }
        }
        this.arr = newArr;
    }

    private float doLoadFactor(){
        // 计算负载因子
        return (usedSize * 1.0f) / arr.length;
    }
}
