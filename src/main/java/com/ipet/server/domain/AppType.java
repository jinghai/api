package com.ipet.server.domain;

/**
 * AppType
 * 
 * @author xiaojinghai
 */
public enum AppType {

	// WEB服务器
	WEBAPP(1),
	// 浏览器
	AGENTAPP(2),
	// 原生应用
	NATIVEAPP(3),
	// 自有应用
	OURAPP(4);

	private int value;

	private AppType() {
	}

	private AppType(int value) {
		this.value = value;
	}

	public static AppType valueOf(int value) {
		for (AppType appType : AppType.values()) {
			if (appType.getValue() == value) {
				return appType;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
