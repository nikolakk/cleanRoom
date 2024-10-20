package com.robot.service.impl;

import com.robot.CleaningRoomException;
import com.robot.MessageResource;
import com.robot.model.InstructionEnum;
import com.robot.model.RoomCleaningRequest;
import com.robot.model.RoomCleaningResponse;
import com.robot.service.CleanRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Qualifier("cleanRoomService")
public class CleanRoomServiceImpl implements CleanRoomService {

    @Autowired
    MessageResource messageResource;

    @Override
    public RoomCleaningResponse cleanRoom(RoomCleaningRequest request) throws CleaningRoomException{

        int[] room = request.getRoom().stream().mapToInt(i -> i).toArray();
        int[] coords = request.getCoords().stream().mapToInt(i -> i).toArray();
        String instructions = request.getInstructions();

        //check if room is not rectangle
        if (room[0]!= room[1]){
            throw new CleaningRoomException(messageResource.get("clean.room.wrong.dimensions"));
        }

        //check if robot coords are outside the room
        if (coords[0]> room[0] || coords[1]> room[1]){
            throw new CleaningRoomException(messageResource.get("clean.room.robot.out.of.room.dimensions"));
        }

        // Convert patches to a set for easier search
        Set<String> patches = new HashSet<>();
        for (List<Integer> patch : request.getPatches()) {
            patches.add(patch.get(0) + "," + patch.get(1));
        }
        return moveRobot(room, coords, patches, instructions);
    }


    private RoomCleaningResponse moveRobot(int[] room, int[] coords,  Set<String> patches, String instructions) {

        int patchCount = 0;

        String[] instructionsArray = instructions.split("");

        for (String instruction : instructionsArray){
            decideNextMove(room, coords, instruction);

            // Check for patches
            patchCount = checkForPatches(coords, patches, patchCount);
        }

        List<Integer> coordsList = Arrays.stream(coords).boxed().collect(Collectors.toList());
        return new RoomCleaningResponse(coordsList, patchCount);
    }

    private static int checkForPatches(int[] coords, Set<String> patches, int patchCount) {
        if (patches.contains(coords[0] + "," + coords[1])) {
            patchCount++;
            patches.remove(coords[0] + "," + coords[1]); // Remove patch after collecting
        }
        return patchCount;
    }

    private static void decideNextMove(int[] room, int[] coords, String instruction) {
        if (InstructionEnum.E.name().equals(instruction)){
            if (coords[0] + 1 < room[0]) coords[0]++;
        } else if (InstructionEnum.W.name().equals(instruction)){
            if (coords[0] - 1 >= 0) coords[0]--;
        } else if (InstructionEnum.N.name().equals(instruction)){
            if (coords[1] + 1 < room[1]) coords[1]++;
        } else if (InstructionEnum.S.name().equals(instruction)){
            if (coords[1] - 1 >= 0) coords[1]--;
        }
    }
}
