package com.georgina.farmshop.service;

import com.georgina.farmshop.exceptions.ProductException;
import com.georgina.farmshop.model.Product;
import com.georgina.farmshop.model.Seller;
import com.georgina.farmshop.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req, Seller seller);

    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    public Product findProductById(Long productId) throws ProductException;
    List<Product> searchProducts(String query);
    // List<Product> findAllProducts();
    public Page<Product> getAllProducts(
            String category,
            String brand,
            String colors,
            String sizes,
            Integer minPrice,
            Integer maxPrice,
            Integer minDiscount,
            String sort,
            String stock,
            Integer pageNumber
    );

    // for the seller's dashboard
    List<Product> getProductBySellerId(Long sellerId);
}
