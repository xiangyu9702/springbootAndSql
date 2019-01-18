package com.example;

import com.example.controller.工作人员;
import com.example.controller.用户;
import com.example.web.User;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Stream;


@SpringBootApplication

public class Application {


	public static void main(String[] args) {
		用户.update();
		工作人员.update();
		SpringApplication.run(Application.class, args);
	}
}

