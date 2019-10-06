package cn.boosp.sharebook.easechat.api.impl;

import cn.boosp.sharebook.easechat.TokenUtil;
import cn.boosp.sharebook.easechat.api.AuthTokenAPI;
import org.springframework.stereotype.Component;

@Component
public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
