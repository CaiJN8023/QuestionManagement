package com.questionManagement;

import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseTest {

	@Before
	public void init() {
		System.out.println("Test Begin------------");
	}
	
	@After
	public void after() {
		System.out.println("Test End--------------");
	}
	
	
	@Test
	public void test1() {
		TreeSet<Integer> tr = new TreeSet<>();
		tr.add(5);
		tr.add(8);
		tr.add(2);
		tr.add(1);
		tr.add(7);
		int num = 1;
		for(int i = 0; i < tr.size(); i++) {
			num = tr.ceiling(num);
			System.out.println(num);
			num ++;
		}
	}
}
