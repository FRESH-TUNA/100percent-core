package com.main.koko_main_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication 를 통해 스프링부트의 자동설정을 진행한다.
 * @SpringBootApplication가 위치한 class부터 읽어들이므로
 * 최상단에 위치시킨다.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //스프링부트의 SpringApplication을 통해
        //외부 톰캣없이 바로 서버를 실행할수 있다.
        SpringApplication.run(Application.class, args);
    }
}
