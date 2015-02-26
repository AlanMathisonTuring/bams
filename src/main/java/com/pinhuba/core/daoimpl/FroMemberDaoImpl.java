package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;
import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：fro_member 对应daoImpl
 */
@Repository
public class FroMemberDaoImpl extends BaseHapiDaoimpl<FroMember, String> implements IFroMemberDao {

    public FroMemberDaoImpl(){
        super(FroMember.class);
    }
}