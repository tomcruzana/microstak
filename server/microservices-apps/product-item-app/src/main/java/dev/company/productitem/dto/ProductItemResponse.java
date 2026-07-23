package dev.company.productitem.dto;

import java.math.BigDecimal;

public record ProductItemResponse(
		int id,
		String category,
		String title,
		String description,
		String photoDataUri,
		BigDecimal price,
		int stockQuantity,
		int rating) {
}
