package cn.boosp.sharebook.easechat.api;


/**
 * This interface is created for RestAPI of Sending Message, it should be
 * synchronized with the API list.
 * 
 * @author Eric23 2016-01-05
 * @see http://docs.easechat.com/
 */
public interface SendMessageAPI {

	Object sendMessage(Object payload);
}
