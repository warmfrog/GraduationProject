package cn.booksp.sharebook.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cn.booksp.sharebook.R;
import cn.booksp.sharebook.ui.activity.MainActivity;

public class IsbnDialog extends DialogFragment {
    EditText isbnEditText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.isbn_dialog, null);
        isbnEditText = view.findViewById(R.id.isbnEditText);

        builder.setView(view).setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String isbn = isbnEditText.getText().toString();
                ((MainActivity) activity).getBook(isbn);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
