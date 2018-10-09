import java.util.Objects;

public class AttrsBean {

    private String id;
    private String cssClass;
    private String href;
    private String title;
    private String rel;
    private String onclick;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    private String style;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttrsBean attrsBean = (AttrsBean) o;
        return Objects.equals(getId(), attrsBean.getId()) &&
                Objects.equals(getCssClass(), attrsBean.getCssClass()) &&
                Objects.equals(getHref(), attrsBean.getHref()) &&
                Objects.equals(getTitle(), attrsBean.getTitle()) &&
                Objects.equals(getRel(), attrsBean.getRel()) &&
                Objects.equals(getOnclick(), attrsBean.getOnclick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCssClass(), getHref(), getTitle(), getRel(), getOnclick());
    }
}
