package com.jc.flora.apps.component.request.trh;

import java.io.Serializable;

public class CacheItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 存储的key */
	public String key;

	/** JSON字符串 */
	public String data;

	/** 过期时间的时间戳 */
	public long timeStamp = 0L;

	public CacheItem(final String key, final String data, final long expiredTime) {
		this.key = key;
		this.timeStamp = System.currentTimeMillis() + expiredTime * 1000;
		this.data = data;
	}
}
