package com.gddlkj.example.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MD5UtilTests {

	@Test
	public void test() {
		System.out.println(MD5Util.generate("12345678"));
	}

}

