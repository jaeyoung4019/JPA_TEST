package toy.project.local_specialty.local_famous_goods.domain.item.food;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import toy.project.local_specialty.local_famous_goods.domain.item.Item;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "food")
@DiscriminatorColumn(name = "food_type")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Food extends Item {
}
