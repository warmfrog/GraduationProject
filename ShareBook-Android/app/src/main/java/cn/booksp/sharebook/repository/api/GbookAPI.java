package cn.booksp.sharebook.repository.api;

import java.util.List;

import cn.booksp.sharebook.domain.Book;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GbookAPI {
    @GET("/api/gbook/isbn/{isbn13}")
    Call<ApiResponse<Book>> getBook(@Path("isbn13") String isbn13);

    @GET("/api/gbook/{id}")
    Call<ApiResponse<Book>> getBook(@Path("id") Integer id);

    @GET("/api/gbook/{isbn13}/image")
    Call<ResponseBody> getImage(@Path("isbn13") String isbn13);

    @GET("/api/gbook")
    Call<ApiResponse<List<Book>>> getBooks(Integer pageIndex, Integer pageSize);
}