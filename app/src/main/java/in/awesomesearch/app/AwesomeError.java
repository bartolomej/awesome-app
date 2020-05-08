package in.awesomesearch.app;

enum ErrorType {
    NETWORK_ERROR,
}

public class AwesomeError extends Exception {

    private ErrorType type;

    private AwesomeError(String message, ErrorType type) {
        super(message);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }

    public static AwesomeError network(String message) {
        return new AwesomeError(message, ErrorType.NETWORK_ERROR);
    }
}
