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

    @Property(nameInDb = "type")
    protected int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
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

    @Generated(hash = 632565145)
    public SearchKeyWords(Long id, String keywords, int type) {
        this.id = id;
        this.keywords = keywords;
        this.type = type;
    }

    @Generated(hash = 1525239630)
    public SearchKeyWords() {
    }

}
