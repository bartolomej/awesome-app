package in.awesomesearch.app;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.AwesomeService;
import retrofit2.Response;

import static org.junit.Assert.*;

public class AwesomeServiceTest {

    @Test
    public void testSearchApi () throws IOException {
        AwesomeService service = AwesomeService.Factory.create();
        Response response = service.search("a").execute();
        ArrayList<AwesomeItem> items = (ArrayList<AwesomeItem>) response.body();

        assertTrue(items.get(0).hasExtras() && items.get(0).hasTags());
        assertEquals(200, response.code());
    }
}