package org.example.springshop.service;

import jakarta.transaction.Transactional;
import org.example.springshop.model.Comment;
import org.example.springshop.model.Product;
import org.example.springshop.model.User;
import org.example.springshop.model.dto.requestmodel.CommentRequestModel;
import org.example.springshop.model.dto.responsemodel.CommentResponseModel;
import org.example.springshop.repository.CommentRepository;
import org.example.springshop.repository.ProductRepository;
import org.example.springshop.repository.UserRepository;
import org.springframework.stereotype.Service;

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

    public CommentResponseModel addComment(CommentRequestModel commentRequestModel) {
        User user = userRepository.findById(commentRequestModel.getUser_id()).orElseThrow();
        Product product = productRepository.findById(commentRequestModel.getProduct_id()).orElseThrow();
        Comment comment = Comment.commentBuilder().commentRequestModel(commentRequestModel).user(user).product(product).build();
        commentRepository.save(comment);
        return CommentResponseModel.builder().comment(comment).build();
    }

    @Transactional
    public CommentResponseModel editComment(Long commentid, Long userid, Long productid, CommentRequestModel commentRequestModel) {
        User user = userRepository.findById(userid).orElseThrow();
        Product product = productRepository.findById(productid).orElseThrow();
        Comment updateComment = commentRepository.findById(commentid).orElseThrow();

        updateComment.setComment(commentRequestModel.getComment());
        updateComment.setStar(commentRequestModel.getStar());
        updateComment.setUser(user);
        updateComment.setProduct(product);
        commentRepository.save(updateComment);
        return CommentResponseModel.builder().comment(updateComment).build();
    }

    public String deleteComment(Long id) {
        commentRepository.deleteById(id);
        return "your comment deleted";
    }
}
