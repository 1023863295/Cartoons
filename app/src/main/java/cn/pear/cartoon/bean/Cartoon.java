package cn.pear.cartoon.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by liuliang on 2017/6/22.
 */
@Entity
public class Cartoon {

    @Id(autoincrement = true)
    protected Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "url")
    protected String url;


    @Generated(hash = 1288418135)
    public Cartoon() {
    }
    @Generated(hash = 752811576)
    public Cartoon(Long id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Cartoon{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
