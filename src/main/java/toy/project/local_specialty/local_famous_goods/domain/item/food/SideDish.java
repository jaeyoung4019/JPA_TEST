package toy.project.local_specialty.local_famous_goods.domain.item.food;

import toy.project.local_specialty.local_famous_goods.domain.item.food.Food;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "sideDish")
public class SideDish extends Food {
}
