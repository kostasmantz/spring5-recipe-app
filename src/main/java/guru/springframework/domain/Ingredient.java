package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@NoArgsConstructor
@Document
public class Ingredient {

    @Id
    private String id;
    private String description;
    private BigDecimal amount;
    @DBRef
    private UnitOfMeasure uom;
    @ToString.Exclude
    @DBRef
    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }
}
