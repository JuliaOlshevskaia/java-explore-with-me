package ru.practicum.mainservice.comments.service;

import ru.practicum.mainservice.comments.dto.CommentDto;
import ru.practicum.mainservice.comments.dto.NewCommentDto;
import java.util.List;

public interface CommentService {
    CommentDto addComment(Integer userId, Integer eventId, NewCommentDto request);

    void deleteComment(Integer commentId, Integer userId);

    List<CommentDto> getComments(Integer eventId, Integer commentatorId, String rangeStart, String rangeEnd,
                                 Integer from, Integer size);

    CommentDto getComment(Integer id);
}
