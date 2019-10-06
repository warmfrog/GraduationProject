package cn.booksp.sharebook.repository.api;

import java.util.List;

import cn.booksp.sharebook.domain.Book;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by warmfrog on 2019/2/27.
 */

public interface BookAPI {
    @POST("/api/book/isbn/{isbn}")
    Call<ApiResponse<Book>> getBook(@Path("isbn") String isbn);

    @GET("/api/book/{id}")
    Call<ApiResponse<Book>> getBook(@Path("id") Integer id);

    @GET("/api/book/{isbn13}/image/s")
    Call<ResponseBody> getImage(@Path("isbn13") String isbn13);

    @GET("/api/book")
    Call<ApiResponse<List<Book>>> getBooks(Integer pageIndex, Integer pageSize);
}
