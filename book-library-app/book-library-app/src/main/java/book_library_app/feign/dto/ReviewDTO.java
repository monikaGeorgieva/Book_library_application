package book_library_app.feign.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewDTO(
        UUID id,
        UUID bookIsbn,
        String username,
        int rating,
        String comment,
        LocalDateTime createdOn,
        LocalDateTime updatedOn
) {}
