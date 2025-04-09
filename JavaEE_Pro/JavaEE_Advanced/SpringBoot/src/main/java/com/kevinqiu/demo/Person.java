package com.kevinqiu.demo;

public class Person {
    private String name;
    private Integer age;
    private String passwd;

    public Person(String name, Integer age, String passwd) {
        this.name = name;
        this.age = age;
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", passwd='" + passwd + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
