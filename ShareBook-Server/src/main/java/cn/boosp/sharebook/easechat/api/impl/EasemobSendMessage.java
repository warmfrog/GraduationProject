package cn.boosp.sharebook.easechat.api.impl;

import cn.boosp.sharebook.easechat.EasemobAPI;
import cn.boosp.sharebook.easechat.OrgInfo;
import cn.boosp.sharebook.easechat.ResponseHandler;
import cn.boosp.sharebook.easechat.TokenUtil;
import cn.boosp.sharebook.easechat.api.SendMessageAPI;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;
import org.springframework.stereotype.Component;

@Component
public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
