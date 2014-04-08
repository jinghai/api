package com.ipet.server.web.rest.base;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.FileSystemResource;

import com.ipet.test.BaseTest;

/**
 * 
 * @author xiaojinghai
 */
public class FilePathTest extends BaseTest {

	@Test
	public void test() throws IOException {
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println(FilePathTest.class.getClassLoader().getResource(""));
		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(FilePathTest.class.getResource(""));

		System.out.println(FilePathTest.class.getResource("/"));// Class文件所在路径

		System.out.println(Thread.currentThread().getContextClassLoader().getResource("."));
		System.out.println(FilePathTest.class.getClassLoader().getResource("."));
		System.out.println(ClassLoader.getSystemResource("."));
		System.out.println(FilePathTest.class.getResource("."));// Class文件所在路径
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(new File("").getAbsolutePath());
		System.out.println(System.getProperty("user.home"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.timezone"));
		System.out.println(System.getProperty("user.language"));
		System.out.println(System.getProperty("user.country"));
		System.out.println(System.getProperty("java.io.tmpdir"));

		System.out.println(new FileSystemResource("").getURI().toString());
		System.out.println(new FileSystemResource("/").getURI().toString());
	}

	public static void main(String[] args) throws IOException {
		new FilePathTest().test();
		System.out.println(new FilePathTest().getTestPhotoPath());
	}

}
