package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;

public interface AreaDao extends JpaRepository<Area, String> {
	@Query(value="select * from T_AREA where C_CITY like ?1 or C_DISTRICT like ?1 or C_PROVINCE like ?1 or upper(C_SHORTCODE) like ?1 or C_CITYCODE like ?1",nativeQuery=true)
	List<Area> findByQ(String q);
	/**
	 * 根据省市区找到区域ID
	 * @param district 
	 * @param city 
	 * @param province 
	 * @return
	 */
	Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
