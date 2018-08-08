package be.g00glen00b.apps.post;

import be.g00glen00b.apps.author.AuthorService;
import be.g00glen00b.apps.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
	private PostRepository repository;
	private CommentService commentService;
	private AuthorService authorService;

	public List<PostListingDTO> findAll() {
		return repository.findAll().stream().map(this::getListingDTO).collect(Collectors.toList());
	}

	public PostInfoDTO findOne(Long id) {
		return repository.findById(id).map(this::getInfoDTO).orElseThrow(PostNotFoundException::new);
	}

	public PostListingDTO getListingDTO(Post entity) {
		return new PostListingDTO(
			entity.getId(),
			entity.getTitle(),
			entity.getPostedAt(),
			authorService.getDTO(entity.getAuthor()));
	}

	public PostInfoDTO getInfoDTO(Post entity) {
		return new PostInfoDTO(
			entity.getId(),
			entity.getTitle(),
			entity.getPostedAt(),
			authorService.getDTO(entity.getAuthor()),
			entity.getContent(),
			entity.getComments().stream().map(commentService::getDTO).collect(Collectors.toList()));
	}
}