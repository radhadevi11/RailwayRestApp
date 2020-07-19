package com.radha.railwayrest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class StationRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void getAllStations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void getAllToStations() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/stations/MAS"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}