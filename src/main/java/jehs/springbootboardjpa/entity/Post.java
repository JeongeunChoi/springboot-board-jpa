package jehs.springbootboardjpa.entity;

import jakarta.persistence.*;
import jehs.springbootboardjpa.dto.PostUpdateRequest;
import jehs.springbootboardjpa.exception.PostErrorMessage;
import jehs.springbootboardjpa.exception.PostException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 59)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    public void updatePost(PostUpdateRequest postUpdateRequest, User user) {
        validateUser(user);
        this.title = postUpdateRequest.getTitle();
        this.content = postUpdateRequest.getContent();
    }

    private void validateUser(User updateUser) {
        if (!updateUser.getId().equals(this.user.getId())) {
            throw new PostException(PostErrorMessage.NOT_POST_BY_USER);
        }
    }

    public enum PostType {
        FREE, INFO, MARKET, EMPLOYMENT
    }
}
