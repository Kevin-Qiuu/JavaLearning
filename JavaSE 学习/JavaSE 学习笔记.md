# JavaSE

## JDK、JRE与JVM之间的关系

## 方法重载

1、方法名称一定相同

2、参数列表要不同（参数的个数、类型以及类型的次序不同）

3、与方法的返回值类型不同

## 方法签名

查看方法签名

![image-20240711171803929](JavaSE 学习笔记_markdown_img/image-20240711171803929.png)

由上图可以看出，方法签名只与方法名、方法参数列表有关，因此方法重载只与这二者相关。

## 动态绑定

不同的类可能存在以下现象，系统调用方法的方式完全相同，但却有着不同的功能，因此可以将具有相同调用方式的方法部分进行抽象，化成父类，并结合向上调整机制，使用父类对象格式进行引用，进而触发多态，这就是动态绑定。

```java
// 尝试多态的代码尝试
// 还没有理解多态的实现原理
// 多态的实现原理大致如下：
// 在 Java 中，所有类都有一个特有的方法表，用于找到对应的方法
// 可以理解为 JVM 管理一个类所有方法的表
// 继承了一个基类的子类，在重写父类方法时，
// 直接将父类的方法表中所对应的这个方法的引用改动了
// 换为子类对这个方法改动后的方法的引用
// 所以再进行向上转移时，将一个子类变量通过基类去引用
// 则只可通过基类引用访问基类的内容，在调用重写方法时，就形成了多态
// 多态实际就是用户（程序员）在使用时，对这一代码场景的描述
// 本质就是子类对基类方法的重写，改动了子类中方法表中基类部分方法表的偏移量
// 进而实现了不同的子类通过它们全部的基类引用调用，对同一行为呈现了不同的动作


// 一个会触发多态的方法
// 触发多态的规则：
// 1、必须在继承体系下
// 2、子类必须要对父类方法重写
// 3、通过父类的引用重写子类方法

// 对于多态的个人理解：
// 一个基类下的不同子类在面对同样的行为时，会有不一样的结果产生，这就是多态
// 比如同为打印机的彩印机和黑白印机，对于打印行为，彩印机印出彩色，黑白印机引出白色
// 一个弓箭，在同一箭尾下，拴上不同的箭头会有不同的威力 
// 拴上火箭头，会着火，拴上挂有物资的箭头，会给予物资
// 但他们都是弓箭，在搭载不一样的箭头下，会有不一样的结果

// 触发多态的条件（触发动态绑定的条件）
// 动态绑定指的意思是父类引用变量在程序运行过程中会动态指向不同子类的变量
// 这个指向的动作称为绑定，而根据具体程序去指向（即起初并不知道会指向什么子类变量）
// 便称为动态绑定
// 1、有继承关系
// 2、向上转型（用一个父类引用变量指向一个子类的动作 ）
// 3、构成方法重写
```

## 接口

对于动态绑定提及的父类，如果完全变成一个最高级抽象，就是接口 `interface` ，对接接口的时候使用关键词 `implements` 。

可以将接口定义为代码中的开发规范，在现实生活中很常见，例如电脑上的 USB 接口：

- U 盘插入，可以存放，提取文件
- 键盘插入，可以通过系统输入流输入信息
- 充电线插入，可以使用电脑的电池进行供电

不同的类在调用同一个方法时，有不同的动作，将其高度抽象，形成接口。继承接口的类都要对接口中的方法进行重写，但是这些类的方法名称都是一样的，进而确保了这些类的开发规范是一致的。

开发中，通过将有相同动作的类进行抽象化，做成对应的接口，然后对接口中的方法进行重写，配合向上调整，构成动态绑定机制，形成多态的调用。~~以 `toString` 方法为例，每一个类都可以重写（`overwrite`）`toString` 方法，（错误理解，这里说的是重写，不是多态）~~



## String

`String` 底层是一个 `char` 类型数组，但 `String` 对象本身没有这个数组的空间，而是通过 `value` 变量引用这个 `char` 数组。

`String=String` ：这是一个浅拷贝。

### `StringBuffer` 与 `StringBuilder`

- 由于 `StringBuffer` 的线程安全性是通过内部同步机制实现的，大部分修改状态的方法都使用了 `synchronized` 关键字，确保方法的原子性，在同一时间内只有一个线程可以执行这些方法，为了保证这些，就会具备更多的同步机制的开销。故在单线程环境中，它的性能通常比`StringBuilder` 差。
- `StringBuilder`由于没有同步机制的开销，因此在单线程环境中性能更好。
- `StringBuffer`和`StringBuilder`都提供了类似的一组方法，如 `append`、`insert`、`delete`、`replace ` 等，用于修改字符串。
- 因为 `StringBuffer` 和 `StringBuilder `的方法中都是直接返回当前的对象，`String `往往都是返回一个新对象，所以这两个对象在进行一些操作（如频繁的 `append` 等）时，会更加高效。

### `String`的`Split`

在Java中，使用`String.split`方法时，如果两个单词之间有两个或更多的空格，那么按空格进行分割时，就会在这两个单词之间产生一个空字符串，进而包含在分割结果数组中。



## 泛型

对于泛型的理解。如果说 Java 引入了面向对象，是对具体事物的一次抽象，那么泛型就是对所有的类别又进行了一次抽象。在编写 Java 代码时，就是对具体业务或事物进行向上的抽象变成类、泛型、接口等语法，随后再重复的进行实例化（具体），把每一次的抽象都具体化。 

泛型在 Java 实现过程中使用到了擦除机制，详细看[这篇博客](https://www.cnblogs.com/wuqinglong/p/9456193.html#1java%E6%B3%9B%E5%9E%8B%E7%9A%84%E5%AE%9E%E7%8E%B0%E6%96%B9%E6%B3%95%EF%BC%9A%E7%B1%BB%E5%9E%8B%E6%93%A6%E9%99%A4)。

## 异常

### 受检查异常

`Exception` 和它的子类都是受查异常，这些异常都会在编译时检查，因此要在会出现这些异常的代码所处的方法开头处使用 `throws` 声明异常，否则程序不能正常通过编译。

### 非受检查异常

`RuntimeException` 和它的子类都是非受查异常，不会在编译时检查。

- **throw** ： 用于抛出异常。
- **try** ： 用于监听。将要被监听的代码(可能抛出异常的代码)放在try[语句块](https://zhida.zhihu.com/search?content_id=242874166&content_type=Article&match_order=1&q=语句块&zhida_source=entity)之内，当try语句块内发生异常时，异常就被抛出。
- **catch** ：用于捕获异常。catch用来捕获try语句块中发生的异常。
- **finally** ： finally语句块总是会被执行。它主要用于回收在[try块](https://zhida.zhihu.com/search?content_id=242874166&content_type=Article&match_order=1&q=try块&zhida_source=entity)里打开的资源(如数据库连接、网络连接和磁盘文件)。 **注意**：只有finally块，执行完成之后，才会回来执行try或者[catch块](https://zhida.zhihu.com/search?content_id=242874166&content_type=Article&match_order=1&q=catch块&zhida_source=entity)中的return或者throw 语句，如果finally中使用了return或者throw等终止方法的语句，则就不会跳回执行，直接停止。
- **throws**： 用在方法签名中，用于声明该方法可能抛出的异常。

### 抛出异常throw

- 那什么时候使用呢？

1. 比如，在定义方法时，方法需要接受参数。那么，当调用方法使用接受到的参数时，首先需要先对参数数据进行合法的判断，数据若不合法，就应该告诉调用者，传递合法的数据进来。这时需要使用抛出异常的方式来告诉调用者。
2. 或者当你觉得解决不了某些异常问题，且不需要调用者处理，那么你也可以抛出异常。

- **throw的作用：**在方法内部抛出一个Throwable 类型的异常。任何Java代码都可以通过throw语句抛出异常。
- 具体如何抛出一个异常呢？

1. 创建一个异常对象。封装一些提示信息(信息可以自己编写)。
2. 需要将这个异常对象告知给调用者。怎么告知呢？怎么将这个异常对象传递到调用者处呢？通过关键字throw就可以完成。throw异常对象。 throw**用在方法内**，用来抛出一个异常对象，将这个异常对象传递到调用者处，并结束当前方法的执行。

**注意**：当方法抛出异常列表的异常时，方法将不对这些类型及其子类类型的异常作处理，而抛向调用该方法的方法，由他去处理。使用throws关键字将异常抛给调用者后，如果调用者不想处理该异常，可以继续向上抛出，但最终要有能够处理该异常的调用者。比如汽车坏了，开车的人也不会修理，只能叫修车公司来修理了。

- 演示一下： 一般来说，throws和 throw通常是成对出现的，例如：

```java
public class ThrowsDemo {
    public static void main(String[] args) throws FileNotFoundException {
        readFile("a.txt");
    }
    // 如果定义功能时有问题发生需要报告给调用者。可以通过在方法上使用throws关键字进行声明
    public static void readFile(String path) throws FileNotFoundException {
        if(!path.equals("a.txt")) {//如果不是 a.txt这个文件
            // 我假设  如果不是 a.txt 认为 该文件不存在 是一个错误 也就是异常  throw
            throw new FileNotFoundException("文件不存在");
        }
    }
}
```

![exception_img](JavaSE 学习笔记_markdown_img/image-20241010115357061-8960296.png)

**产生异常后的代码执行情况：**

```java
public void processData() {
    try {
        step1();        // 可能抛出异常 
        step2();        // 若 step1 抛异常，此处不执行 
    } catch (Exception e) {
        log.error(" 处理异常", e);  // 捕获异常，但未中断 
    }
    step3();            // 无论是否异常，都会执行 
}
```

- 代码执行流程：
  - 若 `step1()` 抛异常 → 执行 `catch` 块 → 执行 `step3()`。
  - 若 `step1()` 无异常 → 执行 `step2()` → 执行 `step3()`。



```java
public void processData() throws CustomException {
    try {
        step1();        // 可能抛出 IOException 
    } catch (IOException e) {
        throw new CustomException("转换异常", e);  // 抛出新异常，方法终止 
    }
    step2();            // 若 catch 中抛出异常，此处不执行 
}
```

- 代码执行流程：
  - 若 `step1()` 抛 `IOException` → `catch` 块抛 `CustomException` → 方法终止，异常传递给调用者。

------

#### **try-catch-finally：**

如果一个线程执行任务时，引发了异常，若不采取捕获异常的操作，线程会直接崩溃终止，不会执行异常的后续代码，所以为了提高代码的健壮性，需要捕获异常：

1. 使用 **try-catch**：如果异常被捕获，线程会继续执行 catch 块及后续代码，但如果没有捕获到异常，则不会执行 catch 块中的代码。
2. 使用 **finally** 块：需要与 try 一起使用，无论是否发生了异常，都会执行 finally 块中的代码。

非常详细的异常内容讲解文章：https://zhuanlan.zhihu.com/p/696348474

## 比较器

Comparable 是一个接口，许多类在比较操作时，会调用 Comparable 接口的方法，所以使用这些类的类需要实现 Comparable 接口。

## Some tips

### `final` 修饰符

`final`修饰的变量值不可以变，但是 Java 中，绝大多数都是引用变量，所以以下情况应要注意。

```java
final int[] arr = new int[]{1, 2, 3, 4, 5};
// 第一种情况
arr = new int[]{1, 2, 3, 4, 5}; // 报错，因为修改了 arr 这个引用变量的值，也就是 array 的地址
// 第二种情况
arr[0] = 1; // 没有问题，因为这是修改的另外一个没有被 final 修饰的空间，故修改不会报错
```

---

# JavaSE 复习

![image-20241010105104340](JavaSE 学习笔记_markdown_img/image-20241010105104340.png)

## 代码块

## 内部类

![image-20241010111035877](JavaSE 学习笔记_markdown_img/image-20241010111035877.png)

---

匿名内部类中的变量不可以发生改变或修改，匿名内部类只可以使用常量或者不对变量进行改动。 

# Java 数据结构

## 装箱和拆箱

显示（装拆箱） or 隐示（装拆箱）





## 用栈实现队列 

![image-20241028122006372](JavaSE 学习笔记_markdown_img/image-20241028122006372.png)



## Map & Set

![image-20241030113449335](JavaSE 学习笔记_markdown_img/image-20241030113449335.png)

### Map

```java
Map<String, int> myMap = new TreeMap<>(); // 显然这里使用了类型推导，编译器根据前面的泛型退出 TreeMap 的泛型
```

![image-20241030113420881](JavaSE 学习笔记_markdown_img/image-20241030113420881.png)

- Map 是一个**接口**，不能直接实例化对象，如果要实例化对象只能实例化其实现类 TreeMap 或者 HashMap . 
- Map 中存放键值对的Key是唯一的，value 是可以重复的
- 在 TreeMap 中插入键值对时，key 不能为空，否则就会抛 NullPointerException 异常，value 可以为空。但是 HashMap 的 key 和value 都可以为空。
- Map 中的 Key 可以全部分离出来，存储到 Set 中来进行访问(因为 Key 不能重复)。
- Map 中的 value 可以全部分离出来，存储在 Collection 的任何一个子集合中(value可能有重复)。
- Map 中键值对的 Key 不能直接修改，value 可以修改，如果要修改 key，只能先将该 key 删除掉，然后再来进行重新插入。
- TreeMap 和 HashMap 的区别

![image-20241030113532416](JavaSE 学习笔记_markdown_img/image-20241030113532416.png)

Map 在底层无论在构造函数中传递的是一个怎样的长度，都会生成一个与传递的数最接近的 2 的 n 次幂的长度作为 Map 的初始长度。



### Set

```java
Set<String> set = new TreeSet<>(); // TreeSet类的底层就是 TreeMap，是一个二叉搜索树
Set<String> set = new HashSet<>();
```

![image-20241030113911716](JavaSE 学习笔记_markdown_img/image-20241030113911716.png)

- Set是继承自Collection的一个接口类
- Set中只存储了key，并且要求key一定要唯一
- TreeSet的底层是使用Map来实现的，其使用 key 与 **Object 的一个默认对象**作为键值对插入到Map中的
- Set最大的功能就是对集合中的元素进行去重 
- 实现Set接口的常用类有TreeSet和HashSet，还有一个LinkedHashSet，LinkedHashSet是在HashSet的基础上维护了一个双向链表来记录元素的插入次序。
- Set中的Key不能修改，如果要修改，先将原来的删除掉，然后再重新插入
- TreeSet中不能插入null的key，HashSet可以。
- TreeSet和HashSet的区别【HashSet在课件最后会讲到】

![image-20241030113933370](JavaSE 学习笔记_markdown_img/image-20241030113933370.png)

### `Map` 与 `Set` 联系

```Java
// map 的底层存储方式是分为两种，一个是二叉排序树，一个是 Hash 函数
// 为此在定义 map 对象时，需要指明选用的底层存储结构是哪一个
// 在 Map 中，每一个键值对视为一个 Entry 对象
// 通过使用 map.entrySet() 方法可以获取到所有的 Entry 对象
// 然后使用 Set 来存放这些 Entry 对象
// Set 的本质就是一个 Map，所以存放的所有元素都是一个键值对（Key + 默认的 Object 对象）
// 在这里 Set 存放的元素类型是 Map.Entry<String, Integer>，也就是 Map 中的 Key
// 同样可以理解为将 Map 的映射关系存放到了 Set 中
// 上面是本质性理解
// 实际在使用时，可以直接将 Set 视为一个集合，是一个不允许存放相同元素的集合，这是因为 Map 的 Key 要唯一
// 所以当需要存放不同元素时，或者需要过滤重复元素时，可以使用 Set 来存放
```

## 通配符

```Java
 
void func2(Set<?> mySet);
```



### 通配符的上界

```Java
<? extends Food>
// 表示最高继承的类只能到 Food，不可以是 Food 的父类，但可以是 Food 的子类
```

### 通配符的下界

```Java
<? super Fruit>
// 表示最低继承的类只能到 Fruit，不可以是 Fruit 的子类，但可以是 Fruit 的父类
```

## 反射

















































































