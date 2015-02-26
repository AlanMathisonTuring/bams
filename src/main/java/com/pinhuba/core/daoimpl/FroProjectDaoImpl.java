package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;

@Repository
public class FroProjectDaoImpl extends BaseHapiDaoimpl<FroProject, String> implements IFroProjectDao {

    public FroProjectDaoImpl(){
        super(FroProject.class);
    }
}