package cps.fs.APImanagerSys.common.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration
public class PageHelperConfig {
	
	public PageHelper pageHelper(){
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("reasonable","true");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
	}
	
}
