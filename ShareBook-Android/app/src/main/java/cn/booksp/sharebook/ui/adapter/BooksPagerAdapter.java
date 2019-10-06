package cn.booksp.sharebook.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import cn.booksp.sharebook.ui.fragments.BookListFrag;

/**
 * Created by warmfrog on 2018/11/27.
 */

public class BooksPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> list ;
    final String []titles = {"新鲜", "IT", "武侠", "言情", "都市", "杂志", "名著", "诗歌"};
    public BooksPagerAdapter(FragmentManager fm){
        super(fm);
        list = new ArrayList<>();
        for(int i=0;i< getCount();i++){
            BookListFrag bookListFrag = new BookListFrag();
            Bundle args = new Bundle();
            args.putString("type", titles[i]);
            bookListFrag.setArguments(args);
            list.add(bookListFrag);
        }
    }

    @Override
    public Fragment getItem(int i){
        return list.get(i);
    }

    @Override
    public int getCount(){
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int postion){
        return titles[postion];
    }
}
