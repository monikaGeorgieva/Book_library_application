package book_library_app.feign.dto;

import java.util.UUID;

public record CreateReviewDTO(
        UUID bookIsbn,
        String username,
        int rating,
        String comment
) {}
