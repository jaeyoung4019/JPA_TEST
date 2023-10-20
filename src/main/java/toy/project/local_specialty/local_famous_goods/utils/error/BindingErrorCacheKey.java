package toy.project.local_specialty.local_famous_goods.utils.error;

import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;

public class BindingErrorCacheKey {
    private final ObjectError value;

    private final LocalDateTime created;

    public BindingErrorCacheKey(ObjectError value) {
        this.value = value;
        this.created = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        return this.value.equals(o);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "CacheKey{" +
                "value=" + value +
                ", created=" + created +
                '}';
    }
}
