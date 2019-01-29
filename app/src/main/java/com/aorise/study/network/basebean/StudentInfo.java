package com.aorise.study.network.basebean;

/**
 * Created by Tuliyuan.
 * Date: 2019/1/29.
 */
public class StudentInfo {
    private String name ;
    private int age;
    private String gender;
    private String classname;

    public StudentInfo(String name, int age, String gender, String classname) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
