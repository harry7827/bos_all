package com.itheima.bos_fore.test;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:applicationContext.xml")
public class RedisTemplateTest {
	//redis操作数据的模板
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	//@Test
	public void test2(){
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		//新增
		/*opsForValue.set("name", "xiaoming");
		//查询
		String nameV = opsForValue.get("name");
		System.out.println(nameV);*/
		
		//修改
//		opsForValue.set("name", "xiaoming2");
//		//查询
//		String nameV2 = opsForValue.get("name");
//		System.out.println(nameV2);
		
		
		//删除
//		redisTemplate.delete("name");
//		String nameV3 = opsForValue.get("name");
//		System.out.println(nameV3);
		
		
		//激活失效时间
//		opsForValue.set("name", "xiaoming", 1, TimeUnit.MINUTES);
//		String nameV4 = opsForValue.get("name");
//		System.out.println(nameV4);
	}
}