package org.example.springshop.controller;

import org.example.springshop.model.dto.requestmodel.CommentRequestModel;
import org.example.springshop.model.dto.responsemodel.CommentResponseModel;
import org.example.springshop.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommentResponseModel addComment(@RequestBody CommentRequestModel commentRequestModel) {
        return commentService.addComment(commentRequestModel);
    }

    @RequestMapping(value = "/list")
    public List<CommentResponseModel> listComment() {
        return commentService.listComment();
    }

    @RequestMapping(value = "/edit/**", method = RequestMethod.POST)
    public CommentResponseModel editComment( @RequestBody CommentRequestModel commentRequestModel) {
        return commentService.editComment(commentRequestModel);
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }
}
