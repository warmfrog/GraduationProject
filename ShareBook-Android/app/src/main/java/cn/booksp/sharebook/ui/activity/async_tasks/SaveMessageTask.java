package cn.booksp.sharebook.ui.activity.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.domain.Message;

public class SaveMessageTask extends AsyncTask<Void,Void,Boolean> {
    private Message message;
    public SaveMessageTask(Message message){
        this.message = message;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        Long messageId = BasicApp.getMessageDao().insert(message);
        Log.d("消息保存到本地,消息ID", messageId.toString());
        return true;
    }
}
