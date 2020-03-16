package pro.butovanton.countries;

import androidx.lifecycle.LiveData;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JSONPlaceHolderApi {
    @GET("rest/v2/all")
    public Call<List<POJO>> getAllPosts();

    @GET("")
    Call<ResponseBody> downloadFlag(@Url String fileUrl);
}
