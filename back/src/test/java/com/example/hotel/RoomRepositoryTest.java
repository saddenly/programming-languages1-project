package com.example.hotel;

import com.example.hotel.model.Room;
import com.example.hotel.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testFindAll() {
        Room room1 = new Room();
        Room room2 = new Room();
        roomRepository.saveAll(List.of(room1, room2));

        List<Room> rooms = roomRepository.findAll();

        assertEquals(7, rooms.size());
        assertTrue(rooms.contains(room1));
        assertTrue(rooms.contains(room2));
    }

    @Test
    public void testFindById() {
        Room room1 = new Room();
        roomRepository.saveAll(List.of(room1));
        Optional<Room> room = roomRepository.findById(room1.getId());
        assertTrue(room.isPresent());
        assertEquals(room1.getId(), room.get().getId());
    }
}
