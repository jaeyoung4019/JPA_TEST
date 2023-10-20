package toy.project.local_specialty.local_famous_goods.domain.item.food;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "desertAndBakery")
public class DesertAndBakery extends Food {
}
