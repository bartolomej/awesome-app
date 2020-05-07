package in.awesomesearch.app.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AwesomeService {

    @GET("search")
    Call<List<AwesomeItem>> search(@Query("q") String q);

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