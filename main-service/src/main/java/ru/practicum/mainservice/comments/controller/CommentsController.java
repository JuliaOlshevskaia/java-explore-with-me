package ru.practicum.mainservice.comments.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.comments.dto.CommentDto;
import ru.practicum.mainservice.comments.dto.NewCommentDto;
import ru.practicum.mainservice.comments.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentsController {
    private final CommentService service;

    @PostMapping("/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable Integer userId,
                                 @PathVariable Integer eventId,
                                 @Valid @RequestBody NewCommentDto request) {

        return service.addComment(userId, eventId, request);
    }

    @DeleteMapping("/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer commentId, @PathVariable Integer userId) {
        service.deleteComment(commentId, userId);
    }

    @GetMapping
    public List<CommentDto> getComments(@RequestParam(name = "eventId", required = false) Integer eventId,
                                        @RequestParam(name = "commentatorId", required = false) Integer commentatorId,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.getComments(eventId, commentatorId, rangeStart, rangeEnd, from, size);
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Integer id) {
        return service.getComment(id);
    }
}
