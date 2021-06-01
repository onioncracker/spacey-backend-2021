package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.productDetail.AddProductDetailDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.productDetail.ProductDetailDto;
import com.heroku.spacey.dto.productDetail.UpdateProductDetailDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MapperProfile {
    private static final ModelMapper mapper = new ModelMapper();

    private MapperProfile() {}

    public static Product adapt(AddProductDto source) {
        mapper.typeMap(AddProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(AddProductDetailDto.class, ProductDetail.class);
        return mapper.map(source, Product.class);
    }

    public static Product adapt(UpdateProductDto source) {
        mapper.typeMap(UpdateProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(UpdateProductDetailDto.class, ProductDetail.class);
        return mapper.map(source, Product.class);
    }

    public static ProductDto adapt(Product source) {
        mapper.typeMap(Product.class, ProductDto.class);
        mapper.typeMap(Material.class, MaterialDto.class);
        mapper.typeMap(Category.class, CategoryDto.class);
        mapper.typeMap(ProductDetail.class, ProductDetailDto.class);
        return mapper.map(source, ProductDto.class);
    }

    public static Category adapt(CategoryDto source) {
        mapper.typeMap(CategoryDto.class, Category.class);
        return mapper.map(source, Category.class);
    }

    public static CategoryDto adapt(Category source) {
        mapper.typeMap(Category.class, CategoryDto.class);
        return mapper.map(source, CategoryDto.class);
    }

    public static Material adapt(MaterialDto source) {
        mapper.typeMap(MaterialDto.class, Material.class);
        return mapper.map(source, Material.class);
    }

    public static MaterialDto adapt(Material source) {
        mapper.typeMap(Material.class, MaterialDto.class);
        return mapper.map(source, MaterialDto.class);
    }
}
