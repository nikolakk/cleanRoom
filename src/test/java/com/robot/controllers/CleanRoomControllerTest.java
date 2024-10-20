package com.robot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CleanRoomControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testCleanRoomValidInput() throws Exception {
            String jsonRequest = "{\n" +
                    "    \"room\": [5, 5],\n" +
                    "    \"coords\": [1, 2],\n" +
                    "    \"patches\": [[1, 0], [2, 2], [2, 3]],\n" +
                    "    \"instructions\": \"NNESEESWNWW\"\n" +
                    "}";

            mockMvc.perform(post("/api/cleanRoom")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonRequest))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.coords[0]").value(1))
                    .andExpect(jsonPath("$.coords[1]").value(3))
                    .andExpect(jsonPath("$.patches").value(1));
        }


    @Test
    public void testCleanRoomWithNoPatches() throws Exception {

        String jsonRequest = "{\n" +
                "    \"room\": [5, 5],\n" +
                "    \"coords\": [1, 1],\n" +
                "    \"patches\": [],\n" +
                "    \"instructions\": \"NNEES\"\n" +
                "}";

        mockMvc.perform(post("/api/cleanRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords[0]").value(3))
                .andExpect(jsonPath("$.coords[1]").value(2))
                .andExpect(jsonPath("$.patches").value(0));
    }

    @Test
    public void testCleanRoomToBoundary() throws Exception {

        String jsonRequest = "{\n" +
                "    \"room\": [5, 5],\n" +
                "    \"coords\": [4, 4],\n" +
                "    \"patches\": [[4, 4]],\n" +
                "    \"instructions\": \"E\"\n" +
                "}";

        mockMvc.perform(post("/api/cleanRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords[0]").value(4))
                .andExpect(jsonPath("$.coords[1]").value(4))
                .andExpect(jsonPath("$.patches").value(1));
    }

    @Test
    public void testCleanRoomWithInvalidCoordinates() throws Exception {

        String jsonRequest = "{\n" +
                "    \"room\": [5, 5],\n" +
                "    \"coords\": [6, 6],\n" +
                "    \"patches\": [[0, 0]],\n" +
                "    \"instructions\": \"N\"\n" +
                "}";

        mockMvc.perform(post("/api/cleanRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest()); // Assuming you add validation for this case
    }

    @Test
    public void testCleanRoomLargeInput() throws Exception {
        StringBuilder patchesBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            patchesBuilder.append(String.format("[%d,%d]", i % 5, i % 5));
            if (i<999){
                patchesBuilder.append(",");
            }
        }

        String jsonRequest = String.format("{\n" +
                "    \"room\": [5, 5],\n" +
                "    \"coords\": [1, 2],\n" +
                "    \"patches\": [%s],\n" +
                "    \"instructions\": \"NNESEESWNWW\"\n" +
                "}", patchesBuilder.toString());

        mockMvc.perform(post("/api/cleanRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}