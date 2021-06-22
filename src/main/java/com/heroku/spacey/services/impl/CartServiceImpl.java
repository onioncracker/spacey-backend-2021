package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CartDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.dto.product.ProductForCartDto;
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
    public void addProductToCart(Long productId, Long sizeId, int amount) {
        Product product = productDao.get(productId);
        Cart cart = getCartForCurrentUser();
        double sum = amount * product.getPrice();

        ProductToCart productToCart = productToCartDao.get(cart.getCartId(), productId, sizeId);
        if (productToCart == null) {
            productToCartDao.insert(cart.getCartId(), productId, sizeId, amount, sum);
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
    public void deleteProductFromCart(Long productId, Long sizeId, int amount) {
        Cart cart = getCartForCurrentUser();
        Product product = productDao.get(productId);
        double sum = amount * product.getPrice();

        ProductToCart productToCart = productToCartDao.get(cart.getCartId(), productId, sizeId);
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
    public Cart getCartForCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        log.debug(principal.toString());
        User user = (User) principal;
        Long cartId = cartDao.getCartIdByUserId(user.getUserId());
        if (cartId == null) {
            cartId = cartDao.createCart(user.getUserId());
        }
        return cartDao.getCart(cartId);
    }

    @Override
    public void cleanCart() {
        Cart cart = getCartForCurrentUser();
        List<ProductToCart> list = productToCartDao.getAllInCart(cart.getCartId());
        for (ProductToCart productToCart: list) {
            productToCartDao.delete(productToCart);
        }
        cartDao.updatePrice(cart.getCartId(), 0);
    }

    @Override
    public List<ProductForCartDto> getAllProductsInCart() {
        Cart cart = getCartForCurrentUser();
        return productToCartDao.getAllProducts(cart.getCartId());
    }
}
