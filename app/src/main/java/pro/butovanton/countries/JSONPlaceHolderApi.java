package pro.butovanton.countries;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/posts/{id}")
    public Call<POJO> getPostWithID(@Path("id") int id);

    @GET("rest/v2/all")
    public Call<List<POJO>> getAllPosts();

    @GET("/posts")
    public Call<List<POJO>> getPostOfUser(@Query("userId") int id);

    @POST("/posts")
    public Call<POJO> postData(@Body POJO data);
}
