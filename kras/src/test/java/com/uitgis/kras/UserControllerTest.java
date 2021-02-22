package com.uitgis.kras;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ComponentScan
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class UserControllerTest {

	private RestDocumentationResultHandler document;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document)
                .build();
	}
	
	@Test
	public void listUser() throws Exception {
		mockMvc.perform(
					get("/api/user")
					//post,put,delete("/api/user")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(document.document(
						responseFields(
							fieldWithPath("[].user_id").description("User UUID"),
							fieldWithPath("[].login_id").description("User Login ID"),
							fieldWithPath("[].passwd").description("User Passwd"),
							fieldWithPath("[].role_type").description("User roles"),
							fieldWithPath("[].password").description("UserDetails password"),
							fieldWithPath("[].enabled").description("UserDetails enabled"),
							fieldWithPath("[].accountNonExpired").description("UserDetails accountNonExpired"),
							fieldWithPath("[].accountNonLocked").description("UserDetails accountNonLocked"),
							fieldWithPath("[].credentialsNonExpired").description("UserDetails credentialsNonExpired"),
							fieldWithPath("[].username").description("UserDetails username"),
							fieldWithPath("[].authorities[]").description("UserDetails authority array"),
							fieldWithPath("[].authorities[].authority").description("UserDetails authority")
						)
				))
				.andExpect(jsonPath("[0].user_id", is(notNullValue())))
				.andExpect(jsonPath("[0].login_id", is(notNullValue())))
				.andExpect(jsonPath("[0].passwd", is(notNullValue())));
		
	}
	
	@Test
	public void viewUser() throws Exception {
		mockMvc.perform(
					get("/api/user/{login_id}", "admin")
						.accept(MediaType.APPLICATION_JSON)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andDo(document.document(
						responseFields(
							fieldWithPath("user_id").description("User UUID"),
							fieldWithPath("login_id").description("User Login ID"),
							fieldWithPath("passwd").description("User Passwd"),
							fieldWithPath("role_type").description("User roles"),
							fieldWithPath("password").description("UserDetails password"),
							fieldWithPath("enabled").description("UserDetails enabled"),
							fieldWithPath("accountNonExpired").description("UserDetails accountNonExpired"),
							fieldWithPath("accountNonLocked").description("UserDetails accountNonLocked"),
							fieldWithPath("credentialsNonExpired").description("UserDetails credentialsNonExpired"),
							fieldWithPath("username").description("UserDetails username"),
							fieldWithPath("authorities[]").description("UserDetails authority array")
							, fieldWithPath("authorities[].authority").description("UserDetails authority")
						)
				))
				.andExpect(jsonPath("$.user_id", is(notNullValue())))
				.andExpect(jsonPath("$.login_id", is(notNullValue())))
				.andExpect(jsonPath("$.passwd", is(notNullValue())));
		
	}
}
