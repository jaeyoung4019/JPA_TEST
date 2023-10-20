package toy.project.local_specialty.local_famous_goods.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchRequest {

    private String category;
    private String keyword;
}
