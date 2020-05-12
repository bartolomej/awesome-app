package in.awesomesearch.app.data.models;

public class UserMessage {

    public int titleResource;
    public int descriptionResource;
    public int imageResource;

    public UserMessage(int titleResource, int descriptionResource) {
        this.titleResource = titleResource;
        this.descriptionResource = descriptionResource;
    }

    public UserMessage(int titleResource, int descriptionResource, int imageResource) {
        this.titleResource = titleResource;
        this.descriptionResource = descriptionResource;
        this.imageResource = imageResource;
    }
}
