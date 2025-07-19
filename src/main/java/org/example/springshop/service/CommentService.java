package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.exception.commentException.CommentNotfoundException;
import org.example.springshop.exception.productException.ProductNotFoundException;
import org.example.springshop.exception.userException.UserNotFoundException;
import org.example.springshop.model.Comment;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.CommentRequestModel;
import org.example.springshop.model.dto.responsemodel.CommentResponseModel;
import org.example.springshop.repository.CommentRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<CommentResponseModel> listComment() {
        List<CommentResponseModel> commentResponseModels = new ArrayList<>();
        commentRepository.findAll().forEach(comment -> {
            CommentResponseModel commentResponseModel = CommentResponseModel.builder().comment(comment).build();
            commentResponseModels.add(commentResponseModel);
        });
        return commentResponseModels;
    }

    public CommentResponseModel addComment(CommentRequestModel commentRequestModel) {
        User user = userRepository.findById(commentRequestModel.getUser_id()).orElseThrow(() -> new UserNotFoundException("user not found"));
        Product product = productRepository.findById(commentRequestModel.getProduct_id()).orElseThrow(() -> new ProductNotFoundException("product not found"));
        Comment comment = Comment.commentBuilder().commentRequestModel(commentRequestModel).user(user).product(product).build();
        commentRepository.save(comment);
        return CommentResponseModel.builder().comment(comment).build();
    }

    @Transactional
    public CommentResponseModel editComment(Long commentId, CommentRequestModel commentRequestModel) {
        Comment updateComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotfoundException("comment not found"));
        updateComment.setComment(commentRequestModel.getComment());
        updateComment.setStar(commentRequestModel.getStar());
        updateComment.setUser(updateComment.getUser());
        updateComment.setProduct(updateComment.getProduct());
        commentRepository.save(updateComment);
        return CommentResponseModel.builder().comment(updateComment).build();
    }

    public String deleteComment(Long id) {
        commentRepository.deleteById(id);
        return "your comment deleted";
    }
}
