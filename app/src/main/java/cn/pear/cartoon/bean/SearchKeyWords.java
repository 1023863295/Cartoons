package cn.pear.cartoon.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuliang on 2017/7/3.
 */

@Entity
public class SearchKeyWords {
    @Id(autoincrement = true)
    protected Long id;

    @Property(nameInDb = "keywords")
    private String keywords;

    @Property(nameInDb = "url")
    protected String url;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 2000214292)
    public SearchKeyWords(Long id, String keywords, String url) {
        this.id = id;
        this.keywords = keywords;
        this.url = url;
    }

    @Generated(hash = 1525239630)
    public SearchKeyWords() {
    }
}
