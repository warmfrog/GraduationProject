package cn.booksp.sharebook.repository;

import android.util.Log;

import java.io.IOException;

import cn.booksp.sharebook.BasicApp;
import cn.booksp.sharebook.domain.Book;
import cn.booksp.sharebook.repository.api.ApiResponse;
import cn.booksp.sharebook.repository.api.GbookAPI;
import cn.booksp.sharebook.repository.dao.BookDao;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by warmfrog on 2019/3/5.
 */
public class BookRepository {
    private static BookRepository bookRepository;
    private BookDao bookDao;
    private GbookAPI gbookAPI;

    private BookRepository() {
        gbookAPI = BasicApp.getGbookAPI();
        bookDao = BasicApp.getBookDao();
    }

    public static BookRepository getInstance() {
        if (null == bookRepository) {
            synchronized (BookRepository.class) {
                if (null == bookRepository) {
                    bookRepository = new BookRepository();
                }
            }
        }
        return bookRepository;
    }

    public Book getBook(String isbn13) {
        Book book = bookDao.findByISBN13(isbn13);
        Log.d("从本地获取图书信息", null == book ? "本地不存在" : book.toString());
        if (null == book) {
            Call<ApiResponse<Book>> bookCall = gbookAPI.getBook(isbn13);
            try {
                Response<ApiResponse<Book>> bookResponse = bookCall.execute();
                ApiResponse<Book> body = bookResponse.body();
                if(body != null) {
                    Book data = body.getData();
                    bookDao.insertAll(data);
                }
            } catch (IOException e) {
                Log.e("网络错误", "从网络获取图书失败, " + e.getMessage());
            }
        } else {
            return book;
        }
        return getBook(isbn13);
    }

    public Book getBook(Integer bookId) {
        Book book = bookDao.findBookById(bookId);
        if (null == book) {
            Call<ApiResponse<Book>> bookCall = gbookAPI.getBook(bookId);
            try {
                Response<ApiResponse<Book>> bookResponse = bookCall.execute();
                ApiResponse<Book> body = bookResponse.body();
                if (null != body) {
                    Book data = body.getData();
                    bookDao.insertAll(data);
                }
            } catch (IOException e) {
                Log.e("网络错误", "从网络获取图书失败, " + e.getMessage());
            }
        } else {
            return book;
        }
        return getBook(bookId);
    }
}
