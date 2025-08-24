package bg.softuni.bikes_shop.controller.rest;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.dto.CommentDTO;
import bg.softuni.bikes_shop.service.CommentService;
import jakarta.validation.Valid;
import org.hibernate.query.QueryParameter;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentsRestController {
    private final RestTemplate restTemplate;
    private final CommentService commentService;

    public CommentsRestController(RestTemplate restTemplate, CommentService commentService) {
        this.restTemplate = restTemplate;
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public Object getAll(){
        return   restTemplate.getForEntity(commentService.getURLForAllComments(),Object[].class);
    }
    @GetMapping("/comment")
    public ResponseEntity<CommentDTO> getSingleComment(@RequestParam String id){
        return  restTemplate.getForEntity(commentService.getURLForOneComment(Long.parseLong(id)), CommentDTO.class);
    }

    @GetMapping("/delete")
    public String deleteComment(@RequestParam String id){
        String url = commentService.getURLForCommentDeletion(Long.parseLong(id));
        System.out.println(url);
          restTemplate.delete(url);
          return "comment deleted";
    }



}

