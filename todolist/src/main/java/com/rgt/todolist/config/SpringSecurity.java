package com.rgt.todolist.config;

import com.rgt.todolist.filter.JWTfilter;
import org.springframework.beans.factory.annotation.Autowired;

public class SpringSecurity {

    @Autowired
    private JWTfilter jwTfilter;
}
