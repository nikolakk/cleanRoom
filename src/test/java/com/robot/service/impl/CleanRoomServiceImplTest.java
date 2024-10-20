package com.robot.service.impl;

import com.robot.CleaningRoomException;
import com.robot.MessageResource;
import com.robot.model.RoomCleaningRequest;
import com.robot.model.RoomCleaningResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CleanRoomServiceImplTest {

    @InjectMocks
    private CleanRoomServiceImpl cleanRoomService;

    @Mock
    private MessageResource messageResource;

    @Test
    void testCleanRoomValidInput() throws CleaningRoomException {

        RoomCleaningRequest request = new RoomCleaningRequest();
        request.setRoom(Arrays.asList(5, 5));
        request.setCoords(Arrays.asList(1, 2));

        List<Integer> patch1 = Arrays.asList(1,0);
        List<Integer> patch2 = Arrays.asList(2,2);
        List<Integer> patch3 = Arrays.asList(2,3);

        List<List<Integer>> patches =  Arrays.asList(patch1,patch2,patch3);
        request.setPatches(patches);
        request.setInstructions("NNESEESWNWW");

        RoomCleaningResponse response = cleanRoomService.cleanRoom(request);

        assertNotNull(response);
        assertNotNull(response.getCoords());
        assertEquals(2, response.getCoords().size());

        assertEquals(1, response.getCoords().get(0));
        assertEquals(3, response.getCoords().get(1));
        assertEquals(1, response.getPatches());

    }


    @Test
    void testCleanRoomWithNoPatches() throws CleaningRoomException {

        RoomCleaningRequest request = new RoomCleaningRequest();
        request.setRoom(Arrays.asList(5, 5));
        request.setCoords(Arrays.asList(1, 1));

        List<List<Integer>> patches =  new ArrayList<>();
        request.setPatches(patches);
        request.setInstructions("NNEES");

        RoomCleaningResponse response = cleanRoomService.cleanRoom(request);

        assertNotNull(response);
        assertNotNull(response.getCoords());
        assertEquals(2, response.getCoords().size());

        assertEquals(3, response.getCoords().get(0));
        assertEquals(2, response.getCoords().get(1));
        assertEquals(0, response.getPatches());

    }

    @Test
    void testCleanRoomToBoundary() throws CleaningRoomException {

        RoomCleaningRequest request = new RoomCleaningRequest();
        request.setRoom(Arrays.asList(5, 5));
        request.setCoords(Arrays.asList(4, 4));

        List<Integer> patch1 = Arrays.asList(4,4);

        List<List<Integer>> patches = Arrays.asList(patch1);
        request.setPatches(patches);
        request.setInstructions("E");

        RoomCleaningResponse response = cleanRoomService.cleanRoom(request);

        assertNotNull(response);
        assertNotNull(response.getCoords());
        assertEquals(2, response.getCoords().size());

        assertEquals(4, response.getCoords().get(0));
        assertEquals(4, response.getCoords().get(1));
        assertEquals(1, response.getPatches());

    }

    @Test
    void testCleanRoomWithRobotInvalidCoordinates() throws CleaningRoomException {

        RoomCleaningRequest request = new RoomCleaningRequest();
        request.setRoom(Arrays.asList(5, 5));
        request.setCoords(Arrays.asList(6, 6));

        List<Integer> patch1 = Arrays.asList(0,0);

        List<List<Integer>> patches = Arrays.asList(patch1);
        request.setPatches(patches);
        request.setInstructions("N");

        Mockito.when(messageResource.get(any()))
                .thenReturn("Invalid input - Robot is outside the room directions.");

        assertThrows(CleaningRoomException.class, () -> {
            cleanRoomService.cleanRoom(request);
        });

    }

    @Test
    void testRoomWithInvalidCoordinates() throws CleaningRoomException {

        RoomCleaningRequest request = new RoomCleaningRequest();
        request.setRoom(Arrays.asList(4, 5));
        request.setCoords(Arrays.asList(4, 4));

        List<Integer> patch1 = Arrays.asList(0,0);

        List<List<Integer>> patches = Arrays.asList(patch1);
        request.setPatches(patches);
        request.setInstructions("N");

        Mockito.when(messageResource.get(any()))
                .thenReturn("Invalid input - Room dimensions are wrong.Room should be rectangle.");

        assertThrows(CleaningRoomException.class, () -> {
            cleanRoomService.cleanRoom(request);
        });

    }
}