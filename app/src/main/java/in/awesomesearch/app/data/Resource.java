package in.awesomesearch.app.data;

import in.awesomesearch.app.AwesomeError;

public class Resource<T> {

    public final T data;
    public final AwesomeError error;

    private Resource (T data, AwesomeError error) {
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success (T data) {
        return new Resource<T>(data, null);
    }

    public static <T> Resource<T> failed (AwesomeError error) {
        return new Resource<T>(null, error);
    }
}
