package com.itheima.bos.vo;

import java.util.ArrayList;
import java.util.List;

public class Series {
	// 数据列名称
	private String name;
	// 数据列id
	private String id;
	// 数据列(series)中每个数据点(point)接受一种颜色
	private boolean colorByPoint = true;
	// 数据列中的数据: 数据点(point)集合
	private List<Point> data = new ArrayList<Point>();

	public List<Point> getData() {
		return data;
	}

	public void setData(List<Point> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isColorByPoint() {
		return colorByPoint;
	}

	public void setColorByPoint(boolean colorByPoint) {
		this.colorByPoint = colorByPoint;
	}

}
