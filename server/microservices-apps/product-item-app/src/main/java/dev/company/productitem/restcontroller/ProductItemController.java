package dev.company.productitem.restcontroller;

import java.util.Base64;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.company.productitem.dto.ProductItemResponse;
import dev.company.productitem.entity.ProductCategory;
import dev.company.productitem.entity.ProductItem;
import dev.company.productitem.repository.ProductCategoryRepository;
import dev.company.productitem.repository.ProductItemRepository;

@RestController
@RequestMapping("/productitemapi")
public class ProductItemController {

	private final ProductItemRepository productItemRepository;
	private final ProductCategoryRepository productCategoryRepository;

	public ProductItemController(ProductItemRepository productItemRepository,
			ProductCategoryRepository productCategoryRepository) {
		this.productItemRepository = productItemRepository;
		this.productCategoryRepository = productCategoryRepository;
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductItemResponse>> getProducts() {
		List<ProductItemResponse> products = productItemRepository.findAll(Sort.by("title"))
				.stream()
				.map(this::toResponse)
				.toList();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductItemResponse> getProduct(@PathVariable int id) {
		return productItemRepository.findById(id)
				.map(this::toResponse)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/categories")
	public ResponseEntity<List<String>> getCategories() {
		List<String> categories = productCategoryRepository.findAll(Sort.by("name"))
				.stream()
				.map(ProductCategory::getName)
				.toList();
		return ResponseEntity.ok(categories);
	}

	private ProductItemResponse toResponse(ProductItem productItem) {
		ProductCategory category = productItem.getProductCategory();
		return new ProductItemResponse(
				productItem.getId(),
				category == null ? "" : category.getName(),
				productItem.getTitle(),
				productItem.getDescription(),
				toPhotoDataUri(productItem.getPhoto()),
				productItem.getPrice(),
				productItem.getStockQuantity(),
				productItem.getRating());
	}

	private String toPhotoDataUri(byte[] photo) {
		if (photo == null || photo.length == 0) {
			return "";
		}
		return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(photo);
	}
}
