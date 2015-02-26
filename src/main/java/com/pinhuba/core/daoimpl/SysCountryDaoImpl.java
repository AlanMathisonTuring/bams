package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_COUNTRY 对应daoImpl
 */
@Repository
public class SysCountryDaoImpl extends BaseHapiDaoimpl<SysCountry, String> implements ISysCountryDao {

   public SysCountryDaoImpl(){
      super(SysCountry.class);
   }
}