package dev.company.productitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.company.productitem.dto.ProductItemResponse;
import dev.company.productitem.entity.ProductCategory;
import dev.company.productitem.entity.ProductItem;
import dev.company.productitem.repository.ProductCategoryRepository;
import dev.company.productitem.repository.ProductItemRepository;
import dev.company.productitem.restcontroller.ProductItemController;

class ProductItemAppApplicationTests {

	private final ProductItemRepository productItemRepository = mock(ProductItemRepository.class);
	private final ProductCategoryRepository productCategoryRepository = mock(ProductCategoryRepository.class);
	private final ProductItemController productItemController = new ProductItemController(
			productItemRepository, productCategoryRepository);

	@Test
	void productsEndpointReturnsProductSummaries() {
		when(productItemRepository.findAll(any(Sort.class))).thenReturn(List.of(productItem()));

		ResponseEntity<List<ProductItemResponse>> response = productItemController.getProducts();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Coffee Gear", response.getBody().get(0).category());
		assertEquals("Demo Mug", response.getBody().get(0).title());
		assertEquals(new BigDecimal("12.95"), response.getBody().get(0).price());
	}

	@Test
	void productDetailEndpointReturnsNotFoundForMissingProduct() {
		when(productItemRepository.findById(404)).thenReturn(Optional.empty());

		ResponseEntity<ProductItemResponse> response = productItemController.getProduct(404);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void categoriesEndpointReturnsNames() {
		ProductCategory category = new ProductCategory();
		category.setName("Coffee Gear");
		when(productCategoryRepository.findAll(any(Sort.class))).thenReturn(List.of(category));

		ResponseEntity<List<String>> response = productItemController.getCategories();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().contains("Coffee Gear"));
	}

	private ProductItem productItem() {
		ProductCategory category = new ProductCategory();
		category.setName("Coffee Gear");

		ProductItem productItem = new ProductItem();
		productItem.setId(1);
		productItem.setProductCategory(category);
		productItem.setTitle("Demo Mug");
		productItem.setDescription("Reusable demo product.");
		productItem.setPrice(new BigDecimal("12.95"));
		productItem.setStockQuantity(12);
		productItem.setRating(4);
		return productItem;
	}
}
