package cn.booksp.sharebook.repository.api;

import android.arch.lifecycle.LiveData;
import android.content.Intent;

import java.math.BigDecimal;
import java.util.List;

import cn.booksp.sharebook.domain.Contact;
import cn.booksp.sharebook.domain.Order;
import cn.booksp.sharebook.domain.Ubook;
import cn.booksp.sharebook.domain.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by warmfrog on 2019/2/25.
 * 使用Retrofit,  A type-safe HTTP client for Android and Java.
 */

public interface UserAPI {
    @GET
    Call<User> getUser(Integer userId);

    @POST("/api/user")
    Call<ApiResponse<User>> register(@Body User user);

    @POST("/login")
    Call<Void> login(@Body User user);

    @POST("/api/user/{username}/release")
    Call<ApiResponse<User>> release(@Path("username") String username, @Body Ubook ubook);

    @GET("/api/user/{username}/balance")
    Call<ApiResponse<BigDecimal>> balance(@Path("username")String username);

    @POST("/api/user/{username}/order/{ubookId}")
    Call<ApiResponse<Order>> order(@Path("username")String username, @Path("ubookId")Integer ubookId, @Query("type")String type, @Query("price")BigDecimal price);

    @POST("/api/user/{username}/cancel/{orderId}")
    Call<ApiResponse<String>> cancel(@Path("username")String username, @Path("orderId")String orderId);

    @POST("/api/user/{username}/pay/{orderId}")
    Call<ApiResponse<String>> pay(@Path("username")String username, @Path("orderId")String orderId);

    @POST("/api/user/{username}/sign/{orderId}")
    Call<ApiResponse<String>> sign(@Path("username")String username, @Path("orderId")String orderId);

    @GET("/api/user/{username}/contact")
    Call<ApiResponse<List<Contact>>> getAllcontact(@Path("username")String username);

    @POST("/api/user/{username}/contact")
    Call<ApiResponse<Object>> addContact(@Path("username")String username, @Body Contact contact);

    @DELETE("/api/user/{username}/delete/{orderId}")
    Call<ApiResponse<String>> delete(@Path("username")String username, @Path("orderId")String orderId);

    @GET("/api/user/{username}/myorder")
    Call<ApiResponse<List<Order>>> myorder(@Path("username")String username);
}