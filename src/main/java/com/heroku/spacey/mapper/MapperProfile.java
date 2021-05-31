package com.heroku.spacey.mapper;

import com.heroku.spacey.dto.category.CategoryDto;
import com.heroku.spacey.dto.category.UpdateCategoryDto;
import com.heroku.spacey.dto.material.MaterialDto;
import com.heroku.spacey.dto.material.UpdateMaterialDto;
import com.heroku.spacey.dto.productDetail.AddProductDetailDto;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.dto.productDetail.UpdateProductDetailDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.entity.Category;
import com.heroku.spacey.entity.Material;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductDetails;
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
        mapper.typeMap(AddProductDetailDto.class, ProductDetails.class);
        return mapper.map(source, Product.class);
    }

    public static Product adapt(UpdateProductDto source) {
        mapper.typeMap(UpdateProductDto.class, Product.class);
        mapper.typeMap(UpdateMaterialDto.class, Material.class);
        mapper.typeMap(UpdateCategoryDto.class, Category.class);
        mapper.typeMap(UpdateProductDetailDto.class, ProductDetails.class);
        return mapper.map(source, Product.class);
    }
}
