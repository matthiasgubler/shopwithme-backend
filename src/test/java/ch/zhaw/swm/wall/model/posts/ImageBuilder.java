package ch.zhaw.swm.wall.model.posts;

import ch.zhaw.swm.wall.model.post.Image;

public class ImageBuilder {

    private String topicId;
    private String personId;

    private ImageBuilder() {
    }

    public static ImageBuilder anImage() {
        return new ImageBuilder();
    }

    public ImageBuilder withTopicId(String topicId) {
        this.topicId = topicId;
        return this;
    }

    public ImageBuilder withPersonId(String personId) {
        this.personId = personId;
        return this;
    }

    public Image build() {
        Image image = new Image();
        image.setTopicId(topicId);
        image.setPersonId(personId);
        return image;
    }

}
