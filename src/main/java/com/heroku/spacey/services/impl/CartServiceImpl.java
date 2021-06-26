package com.heroku.spacey.services.impl;

import com.heroku.spacey.dao.CartDao;
import com.heroku.spacey.dao.ProductDao;
import com.heroku.spacey.dao.ProductToCartDao;
import com.heroku.spacey.dto.cart.EditCartDto;
import com.heroku.spacey.dto.cart.ProductForCartDto;
import com.heroku.spacey.dto.cart.ProductForUnauthorizedCart;
import com.heroku.spacey.entity.Cart;
import com.heroku.spacey.entity.Product;
import com.heroku.spacey.entity.ProductToCart;
import com.heroku.spacey.entity.User;
import com.heroku.spacey.error.NotEnoughProductException;
import com.heroku.spacey.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
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
    @Transactional
    public void addProductToCart(Long productId, Long sizeId, int amount) {
        Product product = productDao.get(productId);
        Cart cart = getCartForCurrentUser();
        if (product.getDiscount() == null) {
            product.setDiscount(0.);
        }
        double sum = amount * (product.getPrice() * ((100 - product.getDiscount()) / 100));

        ProductToCart productToCart = productToCartDao.get(cart.getCartId(), productId, sizeId);
        if (productToCart == null) {
            int available = productDao.getAmount(sizeId, productId);
            if (amount > available) {
                throw new NotEnoughProductException("Cannot add product: product out of stock");
            }
            productToCartDao.insert(cart.getCartId(), productId, sizeId, amount, sum);
            log.info("ProductToCart created");
        } else {
            int newAmount = productToCart.getAmount() + amount;
            int available = productDao.getAmount(sizeId, productId);
            if (newAmount > available) {
                throw new NotEnoughProductException("Cannot add product: product out of stock");
            }
            productToCart.setAmount(newAmount);
            productToCart.setSum(productToCart.getSum() + sum);
            productToCartDao.update(productToCart);
            log.info("ProductToCart updated");
        }

        cartDao.updatePrice(cart.getCartId(), cart.getOverallPrice() + sum);
    }

    @Override
    @Transactional
    public void deleteProductFromCart(Long productId, Long sizeId, int amount) {
        Cart cart = getCartForCurrentUser();
        Product product = productDao.get(productId);
        if (product.getDiscount() == null) {
            product.setDiscount(0.);
        }
        double sum = amount * (product.getPrice() * ((100 - product.getDiscount()) / 100));

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
    @Transactional
    public void cleanCart() {
        Cart cart = getCartForCurrentUser();
        List<ProductToCart> list = productToCartDao.getAllInCart(cart.getCartId());
        for (ProductToCart productToCart : list) {
            productToCartDao.delete(productToCart);
        }
        cartDao.updatePrice(cart.getCartId(), 0);
    }

    @Override
    public List<ProductForCartDto> getAllProductsInCart() {
        Cart cart = getCartForCurrentUser();
        List<ProductForCartDto> list = productToCartDao.getAllProducts(cart.getCartId());
        for (ProductForCartDto product: list) {
            product.setUnavailableAmount(
                getUnavailableCount(
                    product.getId(),
                    product.getSizeId(),
                    product.getAmount()
            ));
        }
        return list;
    }

    @Override
    public List<ProductForCartDto> getUnauthorizedCart(List<EditCartDto> unauthorizedCart) {
        List<ProductForCartDto> list = new ArrayList<>();
        for (EditCartDto cartProduct : unauthorizedCart) {
            ProductForUnauthorizedCart product = productDao.getProductByIdAndSize(
                cartProduct.getProductId(), cartProduct.getSizeId());

            int unavailableAmount;
            if (product.getQuantity() < cartProduct.getAmount()) {
                unavailableAmount = cartProduct.getAmount() - product.getQuantity();
            } else {
                unavailableAmount = 0;
            }

            if (product.getDiscount() == null) {
                product.setDiscount(0.);
            }

            list.add(
                new ProductForCartDto(
                    product.getId(), product.getSizeId(),
                    product.getName(), product.getColor(),
                    product.getSize(), product.getPhoto(),
                    cartProduct.getAmount(),
                    unavailableAmount,
                    product.getDiscount(),
                    cartProduct.getAmount() * product.getPrice()
                ));
        }
        return list;
    }

    private int getUnavailableCount(Long productId, Long sizeId, int amount) {
        int available = productDao.getAmount(sizeId, productId);
        if (amount > available) {
            return amount - available;
        }
        return 0;
    }
}
