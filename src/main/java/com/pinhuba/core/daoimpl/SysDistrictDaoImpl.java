package com.pinhuba.core.daoimpl;

import org.springframework.stereotype.Repository;

import com.pinhuba.core.pojo.*;
import com.pinhuba.core.dao.*;
/**
 * 表：SYS_DISTRICT 对应daoImpl
 */
@Repository
public class SysDistrictDaoImpl extends BaseHapiDaoimpl<SysDistrict, Long> implements ISysDistrictDao {

   public SysDistrictDaoImpl(){
      super(SysDistrict.class);
   }
}