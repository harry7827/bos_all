package com.itheima.bos.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.base.AreaDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.utils.PinYin4jUtils;
@Transactional
@Service("areaService")
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	@Override
	public void save(Area area) {
		areaDao.save(area);
	}
	@Override
	public List<Area> findAll() {
		return areaDao.findAll();
	}
	@Override
	public Page<Area> pageQuery(Pageable pageable) {
		return areaDao.findAll(pageable);
	}
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idList = ids.split(",");
			for (String id : idList) {
				areaDao.delete(id);
			}
		}
	}
	@Override
	public void importXls(File areaFile) throws FileNotFoundException, IOException {
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(areaFile));
		HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum()!=0) {
				Area area = new Area();
				String id=row.getCell(0).getStringCellValue();
				String province=row.getCell(1).getStringCellValue();
				area.setProvince(province);
				province=province.substring(0, province.length()-1);
				
				String city=row.getCell(2).getStringCellValue();
				area.setCity(city);
				city=city.substring(0, city.length()-1);
				
				String district = row.getCell(3).getStringCellValue();
				area.setDistrict(district);
				district=district.substring(0, district.length()-1);
				
				String postcode = row.getCell(4).getStringCellValue();
				String [] citycodes = PinYin4jUtils.getHeadByString(province+city+district);
				String citycode = PinYin4jUtils.stringArrayToString(citycodes);
				String shortcode = PinYin4jUtils.hanziToPinyin(city,"");
				area.setId(id);
				
				area.setPostcode(postcode);
				area.setCitycode(citycode);
				area.setShortcode(shortcode);
				areaDao.save(area);
			}
		}
		hssfWorkbook.close();
	}
	@Override
	public List<Area> findByQ(String q) {
		q="%"+q.toUpperCase()+"%";
		return areaDao.findByQ(q);
	}
}
