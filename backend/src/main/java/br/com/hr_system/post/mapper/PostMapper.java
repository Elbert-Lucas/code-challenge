package br.com.hr_system.post.mapper;

import br.com.hr_system.post.domain.Post;
import br.com.hr_system.post.dto.CreatePostDto;
import br.com.hr_system.post.dto.PostResponseDto;
import br.com.hr_system.user.mapper.UserMapper;
import br.com.hr_system.user.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Autowired
    public PostMapper(UserDetailsService userDetailsService, UserMapper userMapper) {
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    public Post dtoToEntity(CreatePostDto postDto){
        Post post = new ModelMapper().map(postDto, Post.class);
        post.setUser(userDetailsService.findLoggedUser());
        return post;
    }
    public PostResponseDto entityToResponseDto(Post post) {
        PostResponseDto responseDto = new ModelMapper().map(post, PostResponseDto.class);
        responseDto.setUser(userMapper.entityToSimpleDTO(post.getUser()));
        return responseDto;
    }
}
