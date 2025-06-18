package bg.softuni.bikes_shop.service;

public interface CommentService {
    String getURLForOneComment(Long id);

    String getURLForAllComments();

    String getURLForCommentDeletion(Long id);

    String getURLForCommentAddition();
}
