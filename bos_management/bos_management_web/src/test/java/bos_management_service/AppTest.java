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

public class AppTest {
	
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
		
		
		for (SubArea subArea : list) {
			String parent = subArea.getArea().getProvince();
			String outer = subArea.getArea().getCity();
			String inner = subArea.getArea().getDistrict();
			levelCount(cMap,outer,inner);
		}
		Map<String, String> ps = PS(pMap);
		
		List<Series> drilldownSeries=new ArrayList<>();
		//找到所有point 不分类
		Set<Entry<String, Map<String, Integer>>> entrySetC = cMap.entrySet();
		List<Point> points = new ArrayList<>();
		for (Entry<String, Map<String, Integer>> entry : entrySetC) {
			Map<String, Integer> value = entry.getValue();
			for (Entry<String, Integer> entry1 : value.entrySet()) {
				Point point = new Point();
				point.setName(entry1.getKey());
				point.setDrilldown(entry1.getKey());
				point.setY(entry1.getValue());
				points.add(point);
			}
		}
		//准备分类
		Map<String, List<Point>> ppp=new HashMap<>();
		for (Point point : points) {
			String pName = ps.get(point.getName());
			if (ppp.containsKey(pName)) {
				ppp.get(pName).add(point);
			}else {
				ArrayList<Point> arrayList = new ArrayList<Point>();
				arrayList.add(point);
				ppp.put(pName, arrayList);
			}
		}
		
		Set<Entry<String,List<Point>>> entrySet = ppp.entrySet();
		for (Entry<String, List<Point>> entry : entrySet) {
			Series series2 = new Series();
			series2.setId(entry.getKey());
			series2.setName(entry.getKey());
			series2.setData(entry.getValue());
			drilldownSeries.add(series2);
		}
		
		Object jsonP = JSONObject.toJSON(series);
		Object jsonC = JSONObject.toJSON(drilldownSeries);
		System.out.println(jsonP);
		System.out.println("======================");
		System.out.println(jsonC);
	}
	public static void main(String[] args) {
	 /* Long starTime=System.currentTimeMillis();
	  new AppTest().func1();
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
	
	public Map<String, String> PS(Map<String,Map<String,Integer>> map) {
		Map<String, String> ps=new HashMap<>();
		Set<Entry<String,Map<String,Integer>>> entrySet = map.entrySet();
		for (Entry<String, Map<String, Integer>> entry : entrySet) {
			String p = entry.getKey();
			for (Entry<String, Integer> entry2 : entry.getValue().entrySet()) {
				String s = entry2.getKey();
				ps.put(s,p);
			}
		}
		return ps;
	}
}
