package com.ipet.server.app;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring容器启动后初始化工作(暂无)
 * 
 * @author xiaojinghai
 */
@Component
public class AppStartdHandler implements ApplicationListener<ContextRefreshedEvent> {

	@Resource
	private ApplicationContext applicationContext;

	@Resource
	private WebApplicationContext webApplicationContext;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent e) {
		// 判断根容器
		// e.getApplicationContext().getDisplayName().equals("Root WebApplicationContext");
	}

}
