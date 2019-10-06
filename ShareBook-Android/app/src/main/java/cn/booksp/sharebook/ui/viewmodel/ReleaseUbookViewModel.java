package cn.booksp.sharebook.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.domain.Ubook;

/**
 * Created by warmfrog on 2019/2/25.
 */

public class ReleaseUbookViewModel extends ViewModel {
   private static Ubook ubook;
   private static Book book=null;

   public static Ubook getUbook(){
       return ubook;
   }

   public void setBook(Book book){
       this.book = book;
   }

   public Book getBook(){
     return book;
   }

   public void setUbook(Ubook ubook){
       this.ubook = ubook;
   }
}
