package com.city.list.unit;

import com.city.list.service.CityService;
import com.city.list.controller.CityRestController;
import com.city.list.dto.CityDto;
import com.city.list.validator.Validators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CityRestController.class)
@Import(TestSecurityConfig.class)
public class CityRestControllerTest {

    @MockBean
    private CityService cityService;

    @MockBean
    private Validators validators;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getWithPagination_ok_200() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cities?page=0&size=10")
                                .with(httpBasic("admin", "admin")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getWithPagination_unauthorized_401() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cities?page=0&size=10")
                                .with(httpBasic("admin", "admin1")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void searchCity_unauthorized_401() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cities/text?page=0&size=10")
                                .with(httpBasic("user", "usr")))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void searchCity_ok_200() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/v1/cities/text?page=0&size=10")
                                .with(httpBasic("user", "user")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void editCity_forbid_403() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/cities")
                                .with(httpBasic("user", "user")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void editCity_ok_200() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/cities")
                                .content(
                                        new ObjectMapper().writeValueAsString(
                                                new CityDto()
                                                        .setId(1L)
                                                        .setName("string")
                                                        .setPhoto("http://www.photo.com")))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("admin", "admin")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}