package com.robot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "room",
        "coords",
        "patches",
        "instructions"
})
public class RoomCleaningRequest {

    @JsonProperty("room")
    private List<Integer> room;
    @JsonProperty("coords")
    private List<Integer> coords;
    @JsonProperty("patches")
    private List<List<Integer>> patches;
    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("room")
    public List<Integer> getRoom() {
        return room;
    }

    @JsonProperty("room")
    public void setRoom(List<Integer> room) {
        this.room = room;
    }

    @JsonProperty("coords")
    public List<Integer> getCoords() {
        return coords;
    }

    @JsonProperty("coords")
    public void setCoords(List<Integer> coords) {
        this.coords = coords;
    }

    @JsonProperty("patches")
    public List<List<Integer>> getPatches() {
        return patches;
    }

    @JsonProperty("patches")
    public void setPatches(List<List<Integer>> patches) {
        this.patches = patches;
    }

    @JsonProperty("instructions")
    public String getInstructions() {
        return instructions;
    }

    @JsonProperty("instructions")
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningRequest that = (RoomCleaningRequest) o;
        return Objects.equals(room, that.room) && Objects.equals(coords, that.coords) && Objects.equals(patches, that.patches) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room, coords, patches, instructions);
    }

    @Override
    public String toString() {
        return "RoomCleaningRequest{" +
                "room=" + room +
                ", coords=" + coords +
                ", patches=" + patches +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
