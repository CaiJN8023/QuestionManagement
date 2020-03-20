package com.questionManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author CJN
 * @date 2019年3月1日
 * Title: QuestionManagementApplication 
 * Description: SpringBoot项目启动类
 */

@ServletComponentScan
@SpringBootApplication
@ComponentScan(value = "com.questionManagement")
public class QuestionManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(QuestionManagementApplication.class, args);
	}
}
