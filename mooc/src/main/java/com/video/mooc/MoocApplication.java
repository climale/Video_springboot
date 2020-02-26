package com.video.mooc;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;


@SpringBootApplication(scanBasePackages = "com.video")
@EnableTransactionManagement
//@ComponentScan("com.example")

@MapperScan("com.video.Mapper")

public class MoocApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoocApplication.class, args);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize(DataSize.ofMegabytes(30));
		//该方法已降级
		//factory.setMaxRequestSize("30MB");
		/// 设置总上传数据总大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(120));
		return factory.createMultipartConfig();
	}



}
