package book_library_app.feign;

import book_library_app.feign.dto.CreateReviewDTO;
import book_library_app.feign.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "review-service", url = "http://localhost:8081/api/reviews")
public interface ReviewClient {
    @GetMapping("/book/{bookIsbn}")
    List<ReviewDTO> getReviewsForBook(@PathVariable("bookIsbn") UUID bookIsbn);

    @PostMapping
    void addReview(@RequestBody CreateReviewDTO dto);

}
