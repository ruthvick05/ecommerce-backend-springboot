package com.scaler.productservice.models;
import com.scaler.productservice.models.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Getter
@Setter
@Entity(name = "categories")
public class Category extends BaseModel {
    @Column(nullable = false, unique = true)
    private String name;
//    @OneToMany(mappedBy = "category", cascade = {CascadeType.REMOVE}) // mappedBy can be written only on one to many side
            // Without mappedBy → Hibernate creates an extra join table (category_products). ❌
    //With mappedBy → Hibernate uses the category_id in Product, avoiding unnecessary joins. ✅
            //What Join Table Does Hibernate Create?
    //Since Category doesn’t know that Product already has a category_id foreign key, Hibernate automatically generates a new join table (e.g., category_products):
    //
    //Join Table Structure
    //category_id	product_id
    //  1	             101
    //  1	             102
    //  2	             103
    //This table maps Category to Product explicitly, even though the Product table already has a category_id foreign key.
    //
    //This is redundant and unnecessary, leading to extra queries and slower performance.
//    List<Product> products;
}

//Cardinality implementation using hibernate:
//If @manytoone is defined in product class then what happens in different cases when we write different things in category class
//✅ 1. @OneToMany Without mappedBy, Hibernate will:
//        🔸 Create a join table
// 🔸 Use that table to fetch the list of products for a category
// 🔸 Treat Category.products as the owning side of the relationship
//Yes! This join table will look like:
//
//category_products (
//        category_id BIGINT,
//        products_id BIGINT
//)
//
//Hibernate manages the list using this table instead of the foreign key in the product table.
//
//        ✅ 2. With mappedBy = "category", Hibernate will:
//        🔸 Use the foreign key (category_id) in the product table
// 🔸 Recognize that Product.category owns the relationship
// 🔸 Fetch the product list using that foreign key
//Perfect! This is the clean and normalized way to do a bidirectional OneToMany–ManyToOne relationship.
//
//        ✅ 3. With no annotations, Hibernate will:
//        🔸 Ignore the list in the Category class
// 🔸 Treat it as a plain Java field, not a database-mapped relationship
//Correct again!
//No @OneToMany or @Transient = Hibernate doesn't know it's a DB relationship, so it:
//Doesn’t load it
//
//
//Doesn’t persist it
//
//
//Treats it like a local list, not connected to DB
//
//🎯 Summary Table:
//Case
//        Behavior
//DB Impact
//✅ @OneToMany
//        (mappedBy = "category")
//Uses FK in product
//Clean, normalized
//⚠️ @OneToMany only
//        (no mappedBy)
//Creates join table
//Redundant, non-normalized
//❌ No annotation
//Ignored by JPA
//Not mapped at all
//
//
//🔚 Mic Drop Line:
//        "In JPA, who owns the relationship decides the schema — declare it right, or Hibernate builds its own story."























