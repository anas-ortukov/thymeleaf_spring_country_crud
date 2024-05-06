package uz.oasis.model.request;

import java.util.UUID;

public record RegionRequestDto(String name, UUID countryId) {
}
