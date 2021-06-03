package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.dto.productDetail.AddProductDetailDto;
import com.heroku.spacey.dto.productDetail.ProductDetailDto;
import com.heroku.spacey.dto.productDetail.UpdateProductDetailDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConvertor {
    private final ModelMapper mapper;

    public ProductConvertor(@Autowired ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Product adapt(AddProductDto source) {
        mapper.typeMap(AddProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(AddProductDetailDto.class, ProductDetail.class);
        return mapper.map(source, Product.class);
    }

    public Product adapt(UpdateProductDto source) {
        mapper.typeMap(UpdateProductDto.class, Product.class);
        mapper.typeMap(MaterialDto.class, Material.class);
        mapper.typeMap(CategoryDto.class, Category.class);
        mapper.typeMap(UpdateProductDetailDto.class, ProductDetail.class);
        return mapper.map(source, Product.class);
    }

    public ProductDto adapt(Product source) {
        mapper.typeMap(Product.class, ProductDto.class);
        mapper.typeMap(Material.class, MaterialDto.class);
        mapper.typeMap(Category.class, CategoryDto.class);
        mapper.typeMap(ProductDetail.class, ProductDetailDto.class);
        return mapper.map(source, ProductDto.class);
    }
}
