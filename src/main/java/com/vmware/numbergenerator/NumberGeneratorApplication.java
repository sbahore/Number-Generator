
package com.vmware.numbergenerator;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.vmware.numbergenerator")
@EnableSwagger2
@EnableCaching
public class NumberGeneratorApplication {

	public static void main(String[] args) {

		SpringApplication.run(NumberGeneratorApplication.class, args);
	}
}
