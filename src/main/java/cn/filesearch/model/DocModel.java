package cn.filesearch.model;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class DocModel {

    private String title;
    private String hTitle;
    private String content;
    private String hContent;

    private String doctype;


    public DocModel() {
    }

    public DocModel(String title, String hTitle, String content, String hContent, String doctype) {
        this.title = title;
        this.hTitle = hTitle;
        this.content = content;
        this.hContent = hContent;
        this.doctype = doctype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String gethTitle() {
        return hTitle;
    }

    public void sethTitle(String hTitle) {
        this.hTitle = hTitle;
    }

    public String gethContent() {
        return hContent;
    }

    public void sethContent(String hContent) {
        this.hContent = hContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    @Override
    public String toString() {
        return "DocModel{" +
                "title='" + title + '\'' +
                ", hTitle='" + hTitle + '\'' +
                ", hContent='" + hContent + '\'' +
                ", content='" + content + '\'' +
                ", doctype='" + doctype + '\'' +
                '}';
    }
}
