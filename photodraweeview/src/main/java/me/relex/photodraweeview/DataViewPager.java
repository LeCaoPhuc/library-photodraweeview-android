package me.relex.photodraweeview;

/**
 * Interface definition for callback to be invoked when attached ImageView scale changes
 *
 * @author Marek Sebera
 */
public class DataViewPager {
    private String url;
    private TypeOfMedia type;

    public DataViewPager (String url, TypeOfMedia type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public TypeOfMedia getType() {
        return type;
    }

    public void setType(TypeOfMedia type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public enum  TypeOfMedia {
        PHOTO,
        VIDEO;
    }
}
