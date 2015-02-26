package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_ERROR 对应daoImpl
 */
@Repository
public class SysErrorDaoImpl extends BaseHapiDaoimpl<SysError, Long> implements ISysErrorDao {

   public SysErrorDaoImpl(){
      super(SysError.class);
   }
}