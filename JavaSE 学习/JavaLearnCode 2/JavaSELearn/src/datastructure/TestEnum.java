package datastructure;

public enum TestEnum  {
    RED, GREEN, BLUE;
    public static void main(String[] args) {
        for (TestEnum testEnum : TestEnum.values()) {
            System.out.println(testEnum);
        }
    }
}
