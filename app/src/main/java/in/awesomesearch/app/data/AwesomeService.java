package in.awesomesearch.app.data;

import java.util.List;

import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.SearchResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AwesomeService {

    @GET("search")
    Call<SearchResponse> search(@Query("q") String q, @Query("p") int page, @Query("limit") int items);

    @GET("object")
    Call<AwesomeItem> object(@Query("uid") String uid);

    @GET("random")
    Call<AwesomeItem> random(@Query("n") int items);

    class Factory {
        public static AwesomeService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://awesome-web-service.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(AwesomeService.class);
        }
    }
}