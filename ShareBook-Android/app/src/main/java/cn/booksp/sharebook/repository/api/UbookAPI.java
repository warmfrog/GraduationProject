package cn.booksp.sharebook.repository.api;

import java.util.List;

import cn.booksp.sharebook.domain.Ubook;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by warmfrog on 2019/2/25.
 */

public interface UbookAPI {
    @GET("/api/ubook")
    Call<ApiResponse<List<Ubook>>> getUbooks(@Query("pageIndex") Integer pageIndex,@Query("pageSize") Integer pageSize);

    @GET("/api/ubook/{id}")
    Call<ApiResponse<Ubook>> getUbook(@Path("id")Integer ubookId);

    @GET("/api/ubook/{id}/before")
    Call<ApiResponse<List<Ubook>>> getBefore(@Path("id")Integer id, @Query("limit") Integer count);

    @GET("/api/ubook/{id}/after")
    Call<ApiResponse<List<Ubook>>> getAfter(@Path("id")Integer id, @Query("limit") Integer count);

    @GET("/api/ubook/top")
    Call<ApiResponse<List<Ubook>>> getTop(@Query("limit") Integer count);

    @GET("/api/ubook/{username}/count")
    Call<ApiResponse<Integer>> count(@Path("username")String username);

    @GET("/api/ubook/{username}/release")
    Call<ApiResponse<List<Ubook>>> getUbooks(@Path("username")String username);

    @POST("/api/ubook/{username}/some")
    Call<ApiResponse<List<Ubook>>> queryUbooks(@Body List<String> queryUbookIsbns);

    @GET("/api/ubook/{username}/isbn")
    Call<ApiResponse<List<String>>> getUbookIsbns(@Path("username")String username);

    @POST("/api/ubook")
    Call<ApiResponse<Integer>> addUbook(@Body Ubook ubook);

    @DELETE("/api/ubook/{id}")
    Call<ApiResponse<Void>> deleteUbook(@Path("id")Integer id);

    @PUT("/api/ubook")
    Call<ApiResponse<Ubook>> updateUbook(Ubook ubook);



}
