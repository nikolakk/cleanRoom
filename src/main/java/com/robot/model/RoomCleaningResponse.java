package com.robot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coords",
        "patches"
})
public class RoomCleaningResponse {
    @JsonProperty("coords")
    private List<Integer> coords;
    @JsonProperty("patches")
    private Integer patches;

    public RoomCleaningResponse(List<Integer> coords, Integer patchesCleaned) {
        this.coords = coords;
        this.patches = patchesCleaned;
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
    public Integer getPatches() {
        return patches;
    }

    @JsonProperty("patches")
    public void setPatches(Integer patches) {
        this.patches = patches;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomCleaningResponse response = (RoomCleaningResponse) o;
        return Objects.equals(coords, response.coords) && Objects.equals(patches, response.patches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coords, patches);
    }

    @Override
    public String toString() {
        return "RoomCleaningResponse{" +
                "coords=" + coords +
                ", patches=" + patches +
                '}';
    }
}
