package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.hibernate.Hibernate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication

public class JpashopApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("test");
		String data = hello.getData();
		System.out.println(data);
		SpringApplication.run(JpashopApplication.class, args);

	}

}
