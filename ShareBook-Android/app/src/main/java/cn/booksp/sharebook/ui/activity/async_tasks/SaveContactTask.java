package cn.booksp.sharebook.ui.activity.async_tasks;

import android.os.AsyncTask;

import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.repository.ContactRepository;

public class SaveContactTask extends AsyncTask<Void, Void, Boolean> {
    private Contact contact;

    public SaveContactTask(Contact contact) {
        this.contact = contact;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ContactRepository.getInstance().addContact(contact);
        return true;
    }
}
