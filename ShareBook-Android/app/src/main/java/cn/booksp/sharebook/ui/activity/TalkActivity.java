package cn.booksp.sharebook.ui.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.easemob.cloud.EMHttpClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.R;
import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.domain.Message;
import cn.booksp.sharebook.ui.activity.async_tasks.SaveContactTask;
import cn.booksp.sharebook.ui.activity.async_tasks.SaveMessageTask;
import cn.booksp.sharebook.ui.adapter.MessageAdapter;

public class TalkActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageButton btnBack;
    private TextView title;
    private RecyclerView talkBody;
    private Button btnSend;
    private EditText contentView;
    MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btn_back);
        title = findViewById(R.id.toolbar_title);
        talkBody = findViewById(R.id.talkBody);
        btnSend = findViewById(R.id.btnSend);
        contentView = findViewById(R.id.contentView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(talkBody.getContext());
        talkBody.setLayoutManager(layoutManager);
        talkBody.setFocusableInTouchMode(false);
        talkBody.setFocusable(false);
        talkBody.setHasFixedSize(true);

        btnBack.setOnClickListener(view -> {
            finish();
        });

        Intent intent = getIntent();
        String to = intent.getStringExtra("to");
        String from = BasicApp.getUsername();
        toolbar.setTitle("");
        title.setText("用户：" + to);
        setSupportActionBar(toolbar);

        LiveData<List<Message>> messages = BasicApp.getMessageDao().getRecords(from,to);
        adapter = new MessageAdapter(messages.getValue());
        talkBody.setAdapter(adapter);
        messages.observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable List<Message> messages) {
                Log.d("消息结果",messages.toString());
                adapter.setMessages(messages);
                talkBody.scrollToPosition(adapter.getItemCount() -1);
            }
        });

        (new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                List<EMMessage> emMessages = EMChatManager.getInstance().getConversation(to).getAllMessages();
                List<Message> messageList = new ArrayList<>();
                for(EMMessage message : emMessages){
                    messageList.add(new Message(message));
                }
                Log.d("历史消息1", emMessages.toString());
                Log.d("历史消息2", messageList.toString());
                BasicApp.getMessageDao().insertAll(messageList);
                return null;
            }
        }).execute();

        btnSend.setOnClickListener(v -> {
            sendTo(to);
        });

    }

    private void sendTo(String to) {

        EMConversation conversation = EMChatManager.getInstance().getConversation(to);
        EMMessage emMessage = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
        String message = contentView.getText().toString();
        if (message != null && message.trim() != "") {
            contentView.setText("");
            TextMessageBody txtBody = new TextMessageBody(message);
            emMessage.addBody(txtBody);
            emMessage.setReceipt(to);
            emMessage.setFrom(BasicApp.getUsername());
            conversation.addMessage(emMessage);
            Contact contact = new Contact(to,new Date(System.currentTimeMillis()), message);
            new SaveContactTask(contact).execute();

            EMChatManager.getInstance().sendMessage(emMessage, new EMCallBack() {
                @Override
                public void onSuccess() {
                    Message message1 = new Message(emMessage);
                    Log.d("发送消息成功", message1.toString());
                    new SaveMessageTask(message1).execute();
                }

                @Override
                public void onError(int i, String s) {
                    Log.e("发送消息", "发送失败" + s);
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }
}
