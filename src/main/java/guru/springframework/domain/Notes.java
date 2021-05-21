package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Document
public class Notes {

    @Id
    private String id;
    @ToString.Exclude
    @DBRef
    private Recipe recipe;
    private String recipeNotes;

}
