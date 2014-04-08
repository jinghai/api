package com.ipet.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.BeforeClass;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 带DBUnit功能的基础测试类
 * 
 * @author luocanfeng
 * @date 2014年3月29日
 */
public class BaseTestWithDBUnit extends BaseTest {

	protected static final String dbunitPropertiesFile = "dbunit/dbunit.properties";

	protected static final String driverClass;
	protected static final String connectionUrl;
	protected static final String username;
	protected static final String password;
	protected static final String testDataFile; // classpath下的测试数据文件名

	static {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			Resource resource = new ClassPathResource(dbunitPropertiesFile);
			File file = resource.getFile();
			inputStream = new FileInputStream(file);
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		driverClass = properties.getProperty("driverClass");
		connectionUrl = properties.getProperty("connectionUrl");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		testDataFile = properties.getProperty("testDataFile");
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cleanInsert();
	}

	protected static void cleanInsert() throws Exception {
		cleanAndInsertTestData(driverClass, connectionUrl, username, password, testDataFile);
	}

	/**
	 * 清除数据库数据,并插入所给测试数据.
	 * 
	 * @param driverClass
	 *            驱动类
	 * @param connectionUrl
	 *            数据库连接url
	 * @param username
	 *            数据库连接帐号
	 * @param password
	 *            数据库连接密码
	 * @param testDataFile
	 *            classpath下的测试数据文件名
	 */
	private static void cleanAndInsertTestData(String driverClass, String connectionUrl, String username,
			String password, String testDataFile) throws Exception {
		final IDatabaseConnection connection = getConnection(driverClass, connectionUrl, username, password);
		final IDataSet data = getDataSet(testDataFile);
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, data);
		} finally {
			connection.close();
		}
	}

	@SuppressWarnings("deprecation")
	private static IDataSet getDataSet(String testDataFile) throws DataSetException, IOException {
		final Resource resource = new ClassPathResource(testDataFile);
		return new FlatXmlDataSet(resource.getFile(), false, true);
	}

	private static IDatabaseConnection getConnection(String driverClass, String connectionUrl, String username,
			String password) throws ClassNotFoundException, DatabaseUnitException, SQLException {
		Class.forName(driverClass);
		return new DatabaseConnection(DriverManager.getConnection(connectionUrl, username, password));
	}

}
