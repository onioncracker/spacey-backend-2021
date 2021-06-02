package com.heroku.spacey.services;

import com.heroku.spacey.dao.common.UnitOfWorkImpl;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.contracts.ProductService;
import com.heroku.spacey.dto.product.ProductDto;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final UnitOfWorkImpl unitOfWorkImpl;

    public ProductServiceImpl(UnitOfWorkImpl unitOfWorkImpl) {
        this.unitOfWorkImpl = unitOfWorkImpl;
    }

    @Override
    public ProductDto getById(int id) {
        var product = unitOfWorkImpl.getProductDao().get(id);
        if (product == null) {
            return null;
        }
        return MapperProfile.adapt(product);
    }

    @Override
    public void addProduct(AddProductDto addProductDto) {
        var product = MapperProfile.adapt(addProductDto);
        var categoryId = addProductDto.getCategory().getId();
        product.setCategoryId(categoryId);

        var productId = unitOfWorkImpl.getProductDao().insert(product);
        product.getProductDetail().setProductId(productId);
        unitOfWorkImpl.getProductDetailDao().insert(product.getProductDetail());

        for (var i = 0; i < addProductDto.getMaterials().size(); i++) {
            var materialId = addProductDto.getMaterials().get(i).getId();
            unitOfWorkImpl.getProductDao().addMaterialToProduct(materialId, productId);
        }
    }

    @Override
    public void updateProduct(UpdateProductDto updateProductDto) {
        var product = MapperProfile.adapt(updateProductDto);
        unitOfWorkImpl.getProductDao().update(product);
        unitOfWorkImpl.getProductDetailDao().update(product.getProductDetail());
    }

    @Override
    public void removeProduct(int id) {
        var isFound = unitOfWorkImpl.getProductDao().isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        unitOfWorkImpl.getProductDao().remove(id);
    }

    @Override
    public void cancelProduct(int id) {
        var isFound = unitOfWorkImpl.getProductDao().isExist(id);
        if (!isFound) {
            new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        unitOfWorkImpl.getProductDao().delete(id);
    }
}
