package com.pinhuba.core.pojo;

import com.pinhuba.common.annotation.Remark;

/**
 * 数据库表名：fro_news
 */
public class FroNews extends BaseStringBean implements java.io.Serializable {

    @Remark("题标|2|2|1|1")
    private String title;
    
    @Remark("内容|2|2|11|1")
    private String content;
    private String createdate;
    private String attachment;
    
    private SysLibraryInfo newsType;

    //默认构造方法
    public FroNews(){
        super();
    }

    //构造方法(手工生成)


    //get和set方法
    public String getTitle(){
        return title;
    }

    public void setTitle(String aTitle){
        this.title = aTitle;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String aContent){
        this.content = aContent;
    }

    public String getCreatedate(){
        return createdate;
    }

    public void setCreatedate(String aCreatedate){
        this.createdate = aCreatedate;
    }

	public SysLibraryInfo getNewsType() {
		return newsType;
	}

	public void setNewsType(SysLibraryInfo newsType) {
		this.newsType = newsType;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	
}