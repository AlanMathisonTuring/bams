package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：fro_news 对应daoImpl
 */
@Repository
public class FroNewsDaoImpl extends BaseHapiDaoimpl<FroNews, String> implements IFroNewsDao {

    public FroNewsDaoImpl(){
        super(FroNews.class);
    }
}