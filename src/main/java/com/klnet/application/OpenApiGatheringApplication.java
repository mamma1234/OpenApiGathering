package com.klnet.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableAsync
//@EnableScheduling
//@ComponentScan(basePackages = "com.klnet.kmcs")
//@PropertySource("classpath:application.properties")
public class OpenApiGatheringApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApiGatheringApplication.class, args);
//		ConfigurableApplicationContext context = SpringApplication.run(KmcsSpringStarterApplication.class, args);
	}
	
	

//    @Bean
//    public TaskScheduler taskScheduler() {
//        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(10);
//        return scheduler;
//    }


//    @Scheduled(fixedDelay = 2 * 1000L, initialDelay = 3 * 1000L)
//    public void scheduled1() throws InterruptedException {
//        System.out.println(new Date() + " " + Thread.currentThread().getName() + ": scheduled1");
//        Thread.sleep(1000);
//    }
//
//    @Scheduled(fixedDelay = 3 * 1000L, initialDelay = 3 * 1000L)
//    public void scheduled2() throws InterruptedException {
//        System.out.println(new Date() + " " + Thread.currentThread().getName() + ": scheduled2");
//        Thread.sleep(1000);
//    }
    
}
