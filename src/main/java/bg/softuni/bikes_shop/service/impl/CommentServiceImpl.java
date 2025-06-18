package bg.softuni.bikes_shop.service.impl;


import bg.softuni.bikes_shop.configuration.properties.CommentsServerConfigProperties;
import bg.softuni.bikes_shop.service.CommentService;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentsServerConfigProperties commentsServerConfigProperties;

    public CommentServiceImpl(CommentsServerConfigProperties commentsServerConfigProperties) {
        this.commentsServerConfigProperties = commentsServerConfigProperties;
    }


    @Override
    public String getURLForOneComment(Long id) {
        return String.valueOf(compileBaseURL() + "{id}");
    }

    @Override
    public String getURLForAllComments() {
        return String.valueOf(compileBaseURL() + "all");
    }

    @Override
    public String getURLForCommentDeletion(Long id) {
        return String.valueOf(compileBaseURL() + "delete_comment/{id}");
    }

    @Override
    public String getURLForCommentAddition() {
        return String.valueOf(compileBaseURL() + "add_comment");
    }

    private String compileBaseURL() {
        return String.valueOf(new StringBuilder()
                .append(commentsServerConfigProperties.getSchema())
                .append("://")
                .append(commentsServerConfigProperties.getHost())
                .append(":")
                .append(commentsServerConfigProperties.getPort())
                .append(commentsServerConfigProperties.getPath()));
    }

}
