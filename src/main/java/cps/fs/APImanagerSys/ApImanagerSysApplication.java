package cps.fs.APImanagerSys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cps.fs.APImanagerSys.dao")
public class ApImanagerSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApImanagerSysApplication.class, args);
	}
}
