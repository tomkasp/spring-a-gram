package com.greglturnquist.springagram;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.LinkDiscoverers;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ItemTests.TestConfiguration.class)
public class ItemTests {

	protected MockMvc mvc;
	protected static MediaType DEFAULT_MEDIA_TYPE = org.springframework.hateoas.MediaTypes.HAL_JSON;

	@Autowired WebApplicationContext context;
	@Autowired ItemRepository itemRepository;
	@Autowired LinkDiscoverers discoverers;

	@Before
	public void setUp() {

		mvc = MockMvcBuilders.webAppContextSetup(context).//
				defaultRequest(get("/api").accept(DEFAULT_MEDIA_TYPE)).build();
	}

	@Test
	public void htmlUrlTest() throws Exception {

		Item item = new Item();
		item.setImage("foo");
		itemRepository.save(item);

		MvcResult result = mvc.perform(get("/api/items?projection=noItems")).andReturn();
		System.out.println(result.getResponse().getContentAsString());

	}

	@Configuration
	@EnableJpaRepositories(basePackageClasses = Item.class)
	@EnableAutoConfiguration
	public static class TestConfiguration {

	}

}
