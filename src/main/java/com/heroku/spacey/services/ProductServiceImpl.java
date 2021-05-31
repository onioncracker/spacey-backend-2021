package com.heroku.spacey.services;

import com.heroku.spacey.dao.UnitOfWork;
import com.heroku.spacey.dto.product.AddProductDto;
import com.heroku.spacey.contracts.ProductService;
import com.heroku.spacey.dto.product.UpdateProductDto;
import com.heroku.spacey.mapper.MapperProfile;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final UnitOfWork unitOfWork;

    public ProductServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public void addProduct(AddProductDto addProductDto) {
        var product = MapperProfile.adapt(addProductDto);
        var categoryId = unitOfWork.getCategoryDao().insert(product.getCategory());
        product.setCategoryId(categoryId);

        var productId = unitOfWork.getProductDao().insert(product);
        product.getProductDetails().setProductId(productId);
        unitOfWork.getProductDetailDao().insert(product.getProductDetails());

        for (int i = 0; i < addProductDto.getMaterials().size(); i++) {
            var materialId = unitOfWork.getMaterialDao().insert(product.getMaterials().get(i));
            unitOfWork.getProductDao().addMaterialToProduct(materialId, productId);
        }
    }

    @Override
    public void updateProduct(UpdateProductDto updateProductDto) {
        var product = MapperProfile.adapt(updateProductDto);
        unitOfWork.getCategoryDao().update(product.getCategory());
        unitOfWork.getProductDao().update(product);
        unitOfWork.getProductDetailDao().update(product.getProductDetails());
        //
        // unitOfWork.getMaterialDao().update(product.);
    }

    @Override
    public void removeProduct(int id) {
        var product = unitOfWork.getProductDao().getById(id);
        if (product == null) {
            return; //TODO throw exception to frontend
        }
        unitOfWork.getProductDao().remove(id);
    }

    @Override
    public void cancelProduct(int id) {
        var products = unitOfWork.getProductDao().getByIdForDelete(id);
        if (products == null) {
            return; //TODO throw exception to frontend
        }
        unitOfWork.getProductDao().delete(id);
        unitOfWork.getMaterialDao().delete(id);
        unitOfWork.getCategoryDao().delete(id);
        unitOfWork.getProductDetailDao().delete(id);
    }
}
