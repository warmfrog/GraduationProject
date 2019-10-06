package cn.booksp.sharebook;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.domain.Message;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.domain.User;
import cn.booksp.sharebook.repository.api.BookAPI;
import cn.booksp.sharebook.repository.api.GbookAPI;
import cn.booksp.sharebook.repository.api.UbookAPI;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.repository.dao.BookDao;
import cn.booksp.sharebook.repository.dao.ContactDao;
import cn.booksp.sharebook.repository.dao.FileDao;
import cn.booksp.sharebook.repository.dao.MessageDao;
import cn.booksp.sharebook.repository.dao.UbookDao;
import cn.booksp.sharebook.repository.dao.UserDao;
import cn.booksp.sharebook.repository.dao.converter.BigDcimalConverter;
import cn.booksp.sharebook.repository.dao.converter.DateConverter;
import cn.booksp.sharebook.ui.activity.TalkActivity;
import cn.booksp.sharebook.ui.activity.async_tasks.SaveContactTask;
import cn.booksp.sharebook.ui.activity.async_tasks.SaveMessageTask;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by warmfrog on 2019/2/26.
 */

public class BasicApp extends Application {
    static Retrofit retrofit;
    static AppDatabase appDatabase;
    static OkHttpClient client;
    static Context appContext;
    static Executor NETWORK_IO;
    static Executor DISK_IO;
    static UbookDao ubookDao;
    static UbookAPI ubookAPI;
    static BookDao bookDao;
    static BookAPI bookAPI;
    static UserAPI userAPI;
    static GbookAPI gbookAPI;
    static UserDao userDao;
    static ContactDao contactDao;
    static MessageDao messageDao;
    static FileDao fileDao;
    static MutableLiveData<String> username;

    public static String getUsername() {
        if (username == null) {
            username = new MutableLiveData<>();
            SharedPreferences userConfig = appContext.getSharedPreferences("UserConfig", MODE_PRIVATE);
            username.setValue(userConfig.getString("current_user", null));
        }
        return username.getValue();
    }

    public static void setUsername(String name) {
        SharedPreferences userConfig = appContext.getSharedPreferences("UserConfig", MODE_PRIVATE);
        SharedPreferences.Editor edit = userConfig.edit();
        edit.putString("current_user", name).commit();
        username.postValue(name);
    }

    public static UserAPI getUserAPI() {
        if (userAPI == null) {
            synchronized (BasicApp.class) {
                if (userAPI == null) {
                    userAPI = retrofit.create(UserAPI.class);
                }
            }
        }
        return userAPI;
    }

    public static BookAPI getBookAPI() {
        if (bookAPI == null) {
            synchronized (BasicApp.class) {
                if (bookAPI == null) {
                    bookAPI = retrofit.create(BookAPI.class);
                }
            }
        }
        return bookAPI;
    }

    public static GbookAPI getGbookAPI() {
        if (gbookAPI == null) {
            synchronized (BasicApp.class) {
                if (gbookAPI == null) {
                    gbookAPI = retrofit.create(GbookAPI.class);
                }
            }
        }
        return gbookAPI;
    }

    public static UbookAPI getUbookAPI() {
        if (ubookAPI == null) {
            synchronized (BasicApp.class) {
                if (ubookAPI == null) {
                    ubookAPI = retrofit.create(UbookAPI.class);
                }
            }
        }
        return ubookAPI;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            synchronized (BasicApp.class) {
                if (userDao == null) {
                    userDao = appDatabase.userDao();
                }
            }
        }
        return userDao;
    }

    public static UbookDao getUbookDao() {
        if (ubookDao == null) {
            synchronized (BasicApp.class) {
                if (ubookDao == null) {
                    ubookDao = appDatabase.ubookDao();
                }
            }
        }
        return ubookDao;
    }

    public static BookDao getBookDao() {
        if (bookDao == null) {
            synchronized (BasicApp.class) {
                if (bookDao == null) {
                    bookDao = appDatabase.bookDao();
                }
            }
        }
        return bookDao;
    }

    public static MessageDao getMessageDao() {
        if (messageDao == null) {
            synchronized (MessageDao.class) {
                if (messageDao == null) {
                    messageDao = appDatabase.messageDao();
                }
            }
        }
        return messageDao;
    }

    public static ContactDao getContactDao() {
        if (contactDao == null) {
            synchronized (ContactDao.class) {
                if (contactDao == null) {
                    contactDao = appDatabase.contactDao();
                }
            }
        }
        return contactDao;
    }

    public static FileDao fileDao() {
        if (fileDao == null) {
            synchronized (BasicApp.class) {
                if (fileDao == null) {
                    fileDao = FileDao.getInstance(appContext);
                }
            }
        }
        return fileDao;
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static FileDao getFileDao() {
        return FileDao.getInstance(appContext);
    }

    public static Executor getNetworkIO() {
        if (NETWORK_IO == null) {
            synchronized (BasicApp.class) {
                if (NETWORK_IO == null) {
                    NETWORK_IO = Executors.newFixedThreadPool(5);
                }
            }
        }
        return NETWORK_IO;
    }

    public static Executor getDiskIO() {
        if (DISK_IO == null) {
            synchronized (BasicApp.class) {
                if (DISK_IO == null) {
                    DISK_IO = Executors.newSingleThreadExecutor();
                }
            }
        }
        return DISK_IO;
    }

    public static void createNotification(int notifyId, String account, String title, String text) {

        createNotification(notifyId, getNotification(account, title, text));
    }

    public static void createNotification(int notifyId, Notification notification) {
//        NotificationManagerCompat manager = NotificationManagerCompat.from(appContext);
        NotificationManager manager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, notification);
    }

    public static Notification getNotification(String accountName, String title, String text) {
        String MESSAGES_CHANNEL = "message";
        createMessagesNotificationChannel(appContext, MESSAGES_CHANNEL);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                appContext, MESSAGES_CHANNEL
        );
        builder.setGroup(accountName)
                .setSmallIcon(R.drawable.icon_book)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setAutoCancel(true)
                .setPriority(2);

        Intent launchIntent = appContext.getPackageManager().getLaunchIntentForPackage(appContext.getPackageName());

        PendingIntent contentIntent = TaskStackBuilder.create(appContext).addNextIntentWithParentStack(launchIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        Intent resultIntent = new Intent(appContext, TalkActivity.class);
        resultIntent.putExtra("to", title);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(appContext);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        return builder.build();
    }

    public static void createMessagesNotificationChannel(Context context, String MESSAGES_CHANNEL) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.messages_channel_name);
            NotificationChannel channel = new NotificationChannel(
                    MESSAGES_CHANNEL,
                    name,
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void login(String username, String password) {
        String user;
        String pswd;
        if (username == null) {
            user = BasicApp.getUsername();
            pswd = user;
        } else {
            user = username;
            pswd = password;
        }
        if (user != null) {
            EMChatManager.getInstance().login(user, pswd, new EMCallBack() {
                @Override
                public void onSuccess() {
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    Log.d("main", "登录聊天服务器成功");
                }

                @Override
                public void onError(int i, String s) {
                    Log.d("main", "登录聊天服务器失败" + s);
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.d("登录到聊天服务器", "正在登录...");
                }
            });
        }
        NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        appContext.registerReceiver(msgReceiver, intentFilter);
    }

    public static void logout() {
        EMChatManager.getInstance().logout(new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d("退出登录", "成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("退出登录", "失败" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        Log.d("初始化", "应用初始化...");
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request orginalRequest = chain.request();
                SharedPreferences userConfig = getApplicationContext().getSharedPreferences("UserConfig", MODE_PRIVATE);
                String token = userConfig.getString("token", null);

                if (token == null) {
                    return chain.proceed(orginalRequest);
                }
                Request request = orginalRequest.newBuilder().header("Authorization", token).build();
                return chain.proceed(request);
            }
        };
        client = new OkHttpClient.Builder().addInterceptor(mTokenInterceptor).build();
        retrofit = new Retrofit.Builder()
//                .baseUrl("http://47.106.116.17:8080")
                .baseUrl("http://47.106.116.17:8081")
//                .baseUrl("http://192.168.42.44:8081")
//                .baseUrl("http://10.0.2.2:8081")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        appDatabase = AppDatabase.getInstance(this);

        EMChat.getInstance().init(this);
        EMChat.getInstance().setDebugMode(true);
        String username = getUsername();
        login(username, username);

    }

    @Database(entities = {Ubook.class, Book.class, User.class, Message.class, Contact.class}, version = 2)
    @TypeConverters(value = {BigDcimalConverter.class, DateConverter.class})
    public abstract static class AppDatabase extends RoomDatabase {
        public static AppDatabase appDatabase = null;

        public static AppDatabase getInstance(Context appContext) {
            if (appDatabase == null) {
                synchronized (AppDatabase.class) {
                    if (appDatabase == null) {
                        appDatabase = Room.databaseBuilder(appContext, AppDatabase.class, "ubook.db").build();
                        return appDatabase;
                    }
                }
            }
            return appDatabase;
        }

        public abstract UbookDao ubookDao();

        public abstract BookDao bookDao();

        public abstract UserDao userDao();

        public abstract ContactDao contactDao();

        public abstract MessageDao messageDao();
    }

    //消息接收器，每当有新消息，就插入数据库。
    static class NewMessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            abortBroadcast();

            String msgId = intent.getStringExtra("msgid");
            String username = intent.getStringExtra("from");
            EMMessage message = EMChatManager.getInstance().getMessage(msgId);

            //将接受到的消息插入到本地数据库。
            new SaveContactTask(new Contact(username, new Date(System.currentTimeMillis()), new Message(message).getMessage())).execute();
            new SaveMessageTask(new Message(message)).execute();

            if (message.getChatType() == EMMessage.ChatType.GroupChat) {
                username = message.getTo();
            }
            if (!username.equals(username)) {
                return;
            }
            Log.d("收到一条新消息", message.toString());
            String txt = message.getBody().toString().substring(4);
            String from = message.getFrom();
            createNotification(0, username, from, txt);
        }
    }
}
