package toy.project.local_specialty.local_famous_goods.domain.item;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.project.local_specialty.local_famous_goods.dto.enums.PaymentGroup;
import toy.project.local_specialty.local_famous_goods.exception.RestException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "item_type")
public abstract class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @Enumerated(EnumType.STRING)
//    @ElementCollection
//    @CollectionTable(name="item_payment_collection", joinColumns = @JoinColumn(name= "item_id", referencedColumnName = "id"))
    private PaymentGroup payment;
    private String name;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    private int price;
    private int stockQuantity;

    abstract static class Builder<T extends Item.Builder<T>> {

        private PaymentGroup payment;
        private String name;
        private int price;
        private int stockQuantity;
        private List<Category> categories = new ArrayList<>();

        private T addCategory(Category category){
            categories.add(Objects.requireNonNull(category));
            return self();
        }

        public T categories(List<Category> categories){
            this.categories = categories;
            return self();
        }

        public T price(int value){
            this.price = value;
            return self();
        }
        public T stockQuantity(int value){
            this.stockQuantity = value;
            return self();
        }


        public T name(String value){
            this.name = value;
            return self();
        }

        public T payment(PaymentGroup value){
            this.payment = value;
            return self();
        }


        abstract Item build();

        protected abstract T self();
    }

    Item(Item.Builder<?> builder) {
        payment = builder.payment;
        name = builder.name;
        categories = builder.categories;
        stockQuantity = builder.stockQuantity;
        price = builder.price;
    }

    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) throws RestException {
        int i = this.stockQuantity - quantity;
        if ( i < 0 ){
            throw new RestException(403 , "수량이 모자릅니다.");
        }
        this.stockQuantity = i;
    }

}
