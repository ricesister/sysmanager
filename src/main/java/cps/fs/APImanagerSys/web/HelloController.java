package cps.fs.APImanagerSys.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author fs
 * @version 1.0.0
 * @description demo
 * @date 2018年9月3日 下午4:51:13
 */

@Controller
public class HelloController {
	
	@RequestMapping(value="/api",method=RequestMethod.GET)
	public String index() {
		return "login";
	}
	


	/* @RequestMapping("/getUser")
	    public List<Map<String,Object>> getUser(){
	        String sql="select * from initname";
	        List<Map<String,Object>> list=jdbcTemplate.;
	        return list;
	    }*/

}
