package com.itheima.bos.service.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Area;

@Transactional
public interface AreaService {

	void save(Area area);

	List<Area> findAll();

	Page<Area> pageQuery(Pageable pageable);

	void deleteBatch(String ids);

	void importXls(File areaFile) throws FileNotFoundException, IOException;

	List<Area> findByQ(String q);
	
}
