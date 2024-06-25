package com.example.hotel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Base64;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAllRooms() throws Exception {

        ResultActions result = mvc.perform(get("/rooms/all-rooms"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRoomById() throws Exception {
        long userId = 4L;

        ResultActions result = mvc.perform(get("/rooms/room/" + userId));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.roomType").value("single bed"));
    }

    @Test
    public void testCreateRoom() throws Exception {
        ResultActions result = mvc.perform(multipart("/rooms/add/new-room")
                .file("photo", "some content".getBytes())
                .param("roomType", "double bed")
                .param("roomPrice", "500")
                .param("booked", "0")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(user("admin").roles("ADMIN")));

        result.andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost:8080/rooms/room/9"));
    }

    @Test
    public void testUpdateRoom() throws Exception {
        long roomId = 4L;
        byte[] photo = "some content".getBytes();
        String photoBase64 = Base64.getEncoder().encodeToString(photo);

        ResultActions result = mvc.perform(multipart("/rooms/update/" + roomId)

                .file("photo", photo)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                })
                .param("roomType", "single bed")
                .param("roomPrice", "600")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(user("admin").roles("ADMIN")));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomId))
                .andExpect(jsonPath("$.roomPrice").value("600"))
                .andExpect(jsonPath("$.roomType").value("single bed"))
                .andExpect(jsonPath("$.photo").value(photoBase64))
        ;
    }

    @Test
    public void testDeleteRoom() throws Exception {
        long roomId = 8L;

        ResultActions result = mvc.perform(multipart("/rooms/delete/room/" + roomId)
                .with(user("admin").roles("ADMIN"))
                .with(request -> {
                    request.setMethod("DELETE");
                    return request;
                }));

        result.andExpect(status().isNoContent());
    }
}
