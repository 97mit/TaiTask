package co.amdit.taitask.Network;

import com.google.gson.JsonObject;

import java.util.List;
import co.amdit.taitask.Models.ImageListModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("photos")
    Call<List<ImageListModel>> photos();
    @GET("photos")
    Call<JsonObject> getPhotos();

}
