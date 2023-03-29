package com.ajex.temperatureserver;

import com.ajex.temperatureserver.netty.handler.HttpClientMsgHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Log4j2
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
class Tt18Tests {
	@Autowired
	MockMvc mockMvc;

	private String baseUrl ="http://localhost:8088";
	@Autowired
	HttpClientMsgHandler handler;

	@Test
	void getServerConfig() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
								.post(baseUrl)
								// 设置返回值类型为utf-8，否则默认为ISO-8859-1
								.contentType(MediaType.APPLICATION_JSON)
								.content("{\n" +
										"    \"msgtype\": 1,\n" +
										"    \"hw\": \"0407\",\n" +
										"    \"fw\": \"02.00.00.00\",\n" +
										"    \"imei\": \"699999999999999\",\n" +
										"    \"sn\": 1\n" +
										"}")
						//.param("name", "Tom")
				)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
	}

}
