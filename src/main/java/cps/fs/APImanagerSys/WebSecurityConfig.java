package cps.fs.APImanagerSys;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author fs
 * @version 1.0.0
 * @description 登录配置
 * @date 2018年9月4日 上午10:14:24
 */

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter{
	

	public final static String SESSION_KEY = "user";
	

}
