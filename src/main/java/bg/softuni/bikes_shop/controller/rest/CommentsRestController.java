package bg.softuni.bikes_shop.controller.rest;

import bg.softuni.bikes_shop.exceptions.CustomObjectNotFoundException;
import bg.softuni.bikes_shop.model.dto.CommentDTO;
import bg.softuni.bikes_shop.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class CommentsRestController {
    private final RestTemplate restTemplate;
    private final CommentService commentService;

    public CommentsRestController(RestTemplate restTemplate, CommentService commentService) {
        this.restTemplate = restTemplate;
        this.commentService = commentService;
    }

    @GetMapping("/api/comment/{id}")
    public CommentDTO getOneComment(@PathVariable("id") Long id) {
        Map<String, Long> requestPram = Map.of("id", id);
        return restTemplate.getForObject(commentService.getURLForOneComment(id), CommentDTO.class, requestPram);
    }
    @GetMapping("/api/comments/fetch")
    public Object fetchAllComments() {
        return restTemplate.getForEntity(commentService.getURLForAllComments(), Object[].class);
    }

    @PostMapping("/api/comment/add")
    public CommentDTO post( CommentDTO commentDTO) {

        return restTemplate.postForObject(commentService.getURLForCommentAddition(), commentDTO, CommentDTO.class);
    }

    @GetMapping("/api/comment/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        System.out.println(commentService.getURLForCommentDeletion(id));
        restTemplate.delete(commentService.getURLForCommentDeletion(id), id);
    }




}

