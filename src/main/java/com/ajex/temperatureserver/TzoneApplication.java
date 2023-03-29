package com.ajex.temperatureserver;

import com.ajex.temperatureserver.config.netty.WebSocketConfiguration;
import com.ajex.temperatureserver.netty.HttpServer;
import com.ajex.temperatureserver.netty.WebSocketServer;
import com.ajex.temperatureserver.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class TzoneApplication {

	@PostConstruct
	void setDefaultTimezone() {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.of("+3")));
	}

	public static void main(String[] args) {
		SpringApplication.run(TzoneApplication.class, args);
		try {
			WebSocketConfiguration webSocketConfiguration = SpringUtils.getBean(WebSocketConfiguration.class);
			if("httpWeb".equals(webSocketConfiguration.getHttpSocketWeb())){
				HttpServer httpServer = SpringUtils.getBean(HttpServer.class);
				httpServer.start();
			}else{
				WebSocketServer webSocketServer = SpringUtils.getBean(WebSocketServer.class);
				webSocketServer.start();
			}
		} catch (Exception e) {
			log.error("TzoneApplication start fail,error:{}", e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
