package bos_management_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSONObject;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.SubareaService;
import com.itheima.bos.vo.Point;
import com.itheima.bos.vo.Series;
import com.itheima.crm.service.impl.CustomerServiceImpl;

public class AppTest2 {
	
	public void func1() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SubareaService subareaService = (SubareaService)ac.getBean("subareaService");
		List<SubArea> list = subareaService.findAll();
		Map<String,Map<String,Integer>> pMap=new HashMap<>();
		Map<String,Map<String,Integer>> cMap=new HashMap<>();
		for (SubArea subArea : list) {
			String outer = subArea.getArea().getProvince();
			String inner = subArea.getArea().getCity();
			levelCount(pMap, outer, inner);
		}
		for (SubArea subArea : list) {
			String parent = subArea.getArea().getProvince();
			String outer = subArea.getArea().getCity();
			String inner = subArea.getArea().getDistrict();
			levelCount(cMap, outer, inner);
		}
		
		
		Series series=new Series();
		series.setId("");//""
		series.setName("分区统计分布图");//"分区统计分布图"
		List<Point> data = series.getData();
		Set<Entry<String, Map<String, Integer>>> entrySetP = pMap.entrySet();
		for (Entry<String, Map<String, Integer>> entry : entrySetP) {
			Point point = new Point();
			point.setName(entry.getKey());
			point.setDrilldown(entry.getKey());
			//计算数量
			int count=0;
			Map<String, Integer> value = entry.getValue();
			for (Entry<String, Integer> entry1 : value.entrySet()) {
				count += entry1.getValue();
			}
			point.setY(count);
			data.add(point);
		}
		
		/*List<Series> drilldownSeries=new ArrayList<>();
		Set<Entry<String, Map<String, Integer>>> entrySetC = cMap.entrySet();
		for (Entry<String, Map<String, Integer>> entry : entrySetC) {
			Point point = new Point();
			point.setName(entry.getKey());
			point.setDrilldown(entry.getKey());
			//计算数量
			int count=0;
			Map<String, Integer> value = entry.getValue();
			for (Entry<String, Integer> entry1 : value.entrySet()) {
				count += entry1.getValue();
			}
			point.setY(count);
			data.add(point);
		}*/
		
		
		
		Object jsonP = JSONObject.toJSON(pMap);
		Object jsonC = JSONObject.toJSON(cMap);
		System.out.println(jsonP);
		System.out.println("======================");
		System.out.println(jsonC);
	}
	public static void main(String[] args) {
	  /*Long starTime=System.currentTimeMillis();
	  new AppTest2().func1();
	  Long endTime=System.currentTimeMillis();
	  Long Time=endTime-starTime;
	  System.out.println(Time);*/
	}
	
	public void levelCount(Map<String,Map<String,Integer>> map,String outer,String inner) {
		if (map.containsKey(outer)) {
			if (map.get(outer).containsKey(inner)) {
				Integer count = map.get(outer).get(inner)+1;
				map.get(outer).put(inner, count);
			}else {
				map.get(outer).put(inner, 1);
			}
		}else {
			Map<String,Integer> innerMap=new HashMap<>();
			innerMap.put(inner, 1);
			map.put(outer, innerMap);
		}
	}
}
