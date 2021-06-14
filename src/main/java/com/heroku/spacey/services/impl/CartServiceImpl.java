package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CartDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.entity.Cart;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductToCart;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

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
        Product product = productDao.get(productId);
        double sum = amount * product.getPrice();

        Cart cart = getCart(user.getUserId());
        ProductToCart productToCart = productToCartDao.get(cart.getCartId(), productId);
        if (productToCart == null) {
            productToCartDao.insert(cart.getCartId(), productId, amount, sum);
            log.info("ProductToCart created");
        } else {
            productToCart.setAmount(productToCart.getAmount() + amount);
            productToCart.setSum(productToCart.getSum() + sum);
            productToCartDao.update(productToCart);
            log.info("ProductToCart updated");
        }

        cartDao.updatePrice(cart.getCartId(), cart.getOverallPrice() + sum);
    }

    @Override
    public void deleteProductFromCart(Long productId, int amount) {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Cart cart = getCart(user.getUserId());
        Product product = productDao.get(productId);
        double sum = amount * product.getPrice();

        ProductToCart productToCart = productToCartDao.get(cart.getCartId(), productId);
        if (productToCart == null) {
            throw new NotFoundException("product to cart doesnt exist");
        }

        if (productToCart.getAmount() == amount) {
            productToCartDao.delete(productToCart);
            log.info("product to cart deleted");
        } else {
            productToCart.setAmount(productToCart.getAmount() - amount);
            productToCart.setSum(productToCart.getSum() - sum);
            productToCartDao.update(productToCart);
            log.info("ProductToCart updated");
        }
        cartDao.updatePrice(cart.getCartId(), cart.getOverallPrice() - sum);
    }

    @Override
    public Cart getCart(Long userId) {
        Long cartId = cartDao.getCartIdByUserId(userId);
        if (cartId == null) {
            cartId = cartDao.createCart(userId);
        }
        return cartDao.getCart(cartId);
    }

    @Override
    public void cleanCart() {
        User user = (User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Cart cart = getCart(user.getUserId());
        List<ProductToCart> list = productToCartDao.getAllInCart(cart.getCartId());
        for (ProductToCart productToCart: list) {
            productToCartDao.delete(productToCart);
        }
        cartDao.updatePrice(cart.getCartId(), 0);
    }
}
