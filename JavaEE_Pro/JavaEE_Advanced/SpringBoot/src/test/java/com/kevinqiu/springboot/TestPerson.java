package com.kevinqiu.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinqiu.demo.Person;

public class TestPerson {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Person person = new Person("KevinQiu",15,"12345");
        String s = objectMapper.writeValueAsString(person);
        System.out.println(s);
        System.out.println(person);
    }
}
