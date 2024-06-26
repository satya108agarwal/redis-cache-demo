

package com.cache.redis_cache_demo;


import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@RedisHash
public class Student implements Serializable {
    private Long id;
    private String name;
    private String grade;

    public Student(Long id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }
    public Student(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
