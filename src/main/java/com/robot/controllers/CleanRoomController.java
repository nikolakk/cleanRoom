package com.robot.controllers;


import com.robot.model.RoomCleaningRequest;
import com.robot.model.RoomCleaningResponse;
import com.robot.service.CleanRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cleanRoom")
public class CleanRoomController {

    @Autowired
    @Qualifier("cleanRoomService")
    private CleanRoomService cleaningService;

    @PostMapping
    public ResponseEntity<RoomCleaningResponse> cleanRoom(@RequestBody RoomCleaningRequest request) {
        try {
            RoomCleaningResponse response = cleaningService.cleanRoom(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
