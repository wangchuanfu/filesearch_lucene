package cn.filesearch.model;

/**
 * Created by Yao Pan on 17/12/19.
 */
public class DocModel {

    private String title;
    private String content;
    private String doctype;

    public DocModel() {
    }

    public DocModel(String title, String content, String doctype) {
        this.title = title;
        this.content = content;
        this.doctype = doctype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
                ", content='" + content + '\'' +
                ", doctype='" + doctype + '\'' +
                '}';
    }
}
