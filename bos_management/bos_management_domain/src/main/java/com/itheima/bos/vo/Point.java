package com.itheima.bos.vo;

public class Point {
		// 数据点名称
		private String name;
		// 数据点的y值(这里就是各地区数量)
		private Integer y;
		// 用于这个点的下钻功能,下钻展示的数据列默认是隐藏的
		// 下钻的数据列可以通过 id来与父级数据列的 drilldown 进行关联,不再下钻时drilldown值为空
		private String drilldown;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getY() {
			return y;
		}

		public void setY(Integer y) {
			this.y = y;
		}

		public String getDrilldown() {
			return drilldown;
		}

		public void setDrilldown(String drilldown) {
			this.drilldown = drilldown;
		}
	}