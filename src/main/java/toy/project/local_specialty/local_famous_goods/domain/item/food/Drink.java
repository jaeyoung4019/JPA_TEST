package toy.project.local_specialty.local_famous_goods.domain.item.food;

import lombok.AllArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "drink")
@AllArgsConstructor
public class Drink extends Food {
}
