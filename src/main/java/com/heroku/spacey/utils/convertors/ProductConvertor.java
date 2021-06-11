package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Product adapt(AddProductDto source) {
        mapper.typeMap(AddProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        return mapper.map(source, Product.class);
    }

    public Product adapt(UpdateProductDto source) {
        mapper.typeMap(UpdateProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        return mapper.map(source, Product.class);
    }

    public ProductDto adapt(Product source) {
        mapper.typeMap(Product.class, ProductDto.class);
        mapper.typeMap(Material.class, MaterialDto.class);
        mapper.typeMap(Category.class, CategoryDto.class);
        return mapper.map(source, ProductDto.class);
    }
}
