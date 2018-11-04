package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

public interface SubareaDao extends JpaRepository<SubArea,String>,JpaSpecificationExecutor<SubArea>{
	@Query(value="select * from T_SUB_AREA where C_AREA_ID = ?",nativeQuery=true)
	List<SubArea> findByAreaId(String id);

	/**
	 * 分区分布图sql
	 * @return
	 */
	@Query(value="select ta.c_province,count(*) from t_sub_area tsa inner join t_area ta on tsa.c_area_id = ta.c_id group by ta.c_province",nativeQuery=true)
	List<Object[]> doPie();
	
}
