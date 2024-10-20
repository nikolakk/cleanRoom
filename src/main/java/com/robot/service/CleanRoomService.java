package com.robot.service;

import com.robot.CleaningRoomException;
import com.robot.model.RoomCleaningRequest;
import com.robot.model.RoomCleaningResponse;


public interface CleanRoomService {
    RoomCleaningResponse cleanRoom(RoomCleaningRequest request) throws CleaningRoomException;
    }
