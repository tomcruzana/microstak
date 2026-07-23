package dev.company.customer.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
@NamedStoredProcedureQuery(name = "Cart.createCartForRegisteredCustomer", procedureName = "CreateCartForRegisteredCustomer", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "cartId", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "registeredCustomerId", type = Integer.class) })
public class Cart {

	@Id
	private String id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "registered_customer_id", referencedColumnName = "id")
	RegisteredCustomer registeredCustomer;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	List<CartItem> cartItems;

	public Cart() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RegisteredCustomer getRegisteredCustomer() {
		return registeredCustomer;
	}

	public void setRegisteredCustomer(RegisteredCustomer registeredCustomer) {
		this.registeredCustomer = registeredCustomer;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

}
