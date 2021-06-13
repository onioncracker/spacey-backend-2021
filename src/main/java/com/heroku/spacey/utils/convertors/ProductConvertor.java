package com.heroku.spacey.utils.convertors;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.color.ColorDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.dto.size.SizeDto;
import com.heroku.spacey.entity.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {
    private final ModelMapper mapper = new ModelMapper();

    public Product adapt(AddProductDto source) {
        mapper.typeMap(AddProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(ColorDto.class, Color.class);
        mapper.typeMap(SizeDto.class, Size.class);
        return mapper.map(source, Product.class);
    }

    public Product adapt(UpdateProductDto source) {
        mapper.typeMap(UpdateProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(ColorDto.class, Color.class);
        mapper.typeMap(SizeDto.class, Size.class);
        return mapper.map(source, Product.class);
    }

    public ProductDto adapt(Product source) {
        mapper.typeMap(Product.class, ProductDto.class);
        mapper.typeMap(Material.class, MaterialDto.class);
        mapper.typeMap(Category.class, CategoryDto.class);
        mapper.typeMap(Color.class, ColorDto.class);
        mapper.typeMap(Size.class, SizeDto.class);
        return mapper.map(source, ProductDto.class);
    }
}
