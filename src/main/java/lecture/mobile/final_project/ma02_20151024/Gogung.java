package lecture.mobile.final_project.ma02_20151024;

public class Gogung {

    private int _id;

    private String gungNumber;
    private String contentsIndex;
    private String contents;
    private String explanation;
    private String imgUrl;

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id;  }

    public String getGungNumber() {
        return gungNumber;
    }

    public void setGungNumber(String gungNumber) {
        this.gungNumber = gungNumber;
    }

    public String getContentsIndex() { return contentsIndex; }

    public void setContentsIndex(String contentsIndex) { this.contentsIndex = contentsIndex; }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
