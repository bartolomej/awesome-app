package in.awesomesearch.app;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.AwesomeService;
import in.awesomesearch.app.data.models.SearchResponse;
import retrofit2.Response;

import static org.junit.Assert.*;

public class AwesomeServiceTest {

    @Test
    public void testSearchApi () throws IOException {
        AwesomeService service = AwesomeService.Factory.create();
        Response response = service.search("a", 0, 5).execute();
        SearchResponse searchResponse = (SearchResponse) response.body();

        // NOTICE: this test is dependant on external REST service !!
        assertEquals(5, searchResponse.next);
        assertEquals(0, searchResponse.page);
        assertEquals(5, searchResponse.result.size());
        assertEquals(200, response.code());
    }
}