package br.com.hr_system.post.service;

import br.com.hr_system.post.domain.Post;
import br.com.hr_system.post.dto.CreatePostDto;
import br.com.hr_system.post.dto.PostResponseDto;
import br.com.hr_system.post.mapper.PostMapper;
import br.com.hr_system.post.repository.PostRepository;
import br.com.hr_system.shared.dto.CreatedMessageResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;
    @Autowired
    public PostService(PostRepository postRepository, PostMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public Page<PostResponseDto> getPosts(Pageable pageable){
        return postRepository.findAll_ByOrderByIdDesc(pageable)
               .map(mapper::entityToResponseDto);
    }

    public CreatedMessageResponseDTO createPost(CreatePostDto postDto) {
        postRepository.save(mapper.dtoToEntity(postDto));
        return new CreatedMessageResponseDTO("Post criado com sucesso");
    }
}
