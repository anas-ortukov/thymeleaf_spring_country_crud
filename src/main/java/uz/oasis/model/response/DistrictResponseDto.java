package uz.oasis.model.response;

import java.util.UUID;

public record DistrictResponseDto(UUID id, String name, String region, String country) {
}
