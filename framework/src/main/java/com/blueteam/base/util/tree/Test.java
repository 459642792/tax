package com.blueteam.base.util.tree;

/**
 * 树公用类目
 *
 * @author xiaojiang
 * @create 2017-07-26  19:24
 */
public class Test {
    private String id;
    private String pid;
    private String text;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Test(String id, String pid, String text, String status) {
        super();
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.status = status;
    }

    public Test() {
        super();
    }

    @Override
    public String toString() {
        return "Test [id=" + id + ", pid=" + pid + ", text=" + text + ",status=" + status + "]";
    }
}
