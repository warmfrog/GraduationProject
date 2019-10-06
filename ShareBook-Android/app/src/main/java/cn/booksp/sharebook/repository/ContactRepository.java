package cn.booksp.sharebook.repository;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.UserAPI;
import cn.booksp.sharebook.repository.dao.ContactDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactRepository {
    private static ContactRepository contactRepository;
    private ContactDao contactDao = BasicApp.getContactDao();
    private UserAPI userAPI = BasicApp.getUserAPI();

    private ContactRepository(){
    }

    public static ContactRepository getInstance(){
            if(contactRepository == null){
                synchronized (ContactRepository.class)    {
                    if(contactRepository == null){
                        contactRepository = new ContactRepository();
                    }
                }
            }
        return contactRepository;
    }

    public void addContact(Contact contact){
        String username = contact.getName();
        if(contactDao.get(username) == null){
            userAPI.addContact(BasicApp.getUsername(),contact).enqueue(new Callback<ApiResponse<Object>>() {
                @Override
                public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                    ApiResponse<Object> body = response.body();
                    if(body != null) {
                        if (body.getStatus() == ApiResponse.Status.ok) {
                            Log.d("添加到服务器数据库", "成功");
                        }
                    }else {
                        Log.d("从服务器获取错误","body为空");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                    Log.d("添加到服务器数据库","失败");
                }
            });
        };
        (new Thread(){
            @Override
            public void run() {
                contactDao.insert(contact);
            }
        }).run();

    }

    public LiveData<List<Contact>> getContacts(String username){
        LiveData<List<Contact>> all = contactDao.findAll();
        if(all.getValue() == null){
            Log.d("本地通讯录信息为空","从服务器加载");
            Call<ApiResponse<List<Contact>>> contactCall = userAPI.getAllcontact(username);
            try {
                Response<ApiResponse<List<Contact>>> execute = contactCall.execute();
                ApiResponse<List<Contact>> body = execute.body();
                if(body!=null) {
                    List<Contact> data = body.getData();
                    Log.d("从服务器获取到的通讯录信息", data.toString());
                    contactDao.insertAll(data);
                    all = contactDao.findAll();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return all;
    }
}
