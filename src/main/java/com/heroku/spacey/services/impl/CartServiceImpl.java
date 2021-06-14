package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CartDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.entity.Cart;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private CartDao cartDao;
    private ProductDao productDao;
    private ProductToCartDao productToCartDao;

    public CartServiceImpl(@Autowired CartDao cartDao,
                           @Autowired ProductDao productDao,
                           @Autowired ProductToCartDao productToCartDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.productToCartDao = productToCartDao;
    }

    @Override
    public void addProductToCart(Long productId, int amount) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Cart cart = getCart(user.getUserId());
        Product product = productDao.get(productId);
        double sum = amount * product.getPrice();
        productToCartDao.insert(cart.getCartId(), productId, amount, sum);
        cartDao.updatePrice(cart.getCartId(), cart.getOverallPrice() + sum);
    }

    @Override
    public void deleteProductFromCart(Long productId, int amount) {
        // TODO implement delete
    }

    @Override
    public Cart getCart(Long userId) {
        Long cartId = cartDao.getCartIdByUserId(userId);
        if (cartId == null) {
            cartId = cartDao.createCart(userId);
        }
        return cartDao.getCart(cartId);
    }
}
