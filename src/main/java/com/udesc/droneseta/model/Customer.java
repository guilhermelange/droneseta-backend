package com.udesc.droneseta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {
	public Customer() {}
	
	public Customer(Integer id, @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres") String name,
			@Size(min = 11, max = 11, message = "O CPF deve possuir 11 caracteres") String cpf, String creditCard) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.creditCard = creditCard;
	}

	public Customer(Integer id, @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres") String name,
					@Size(min = 11, max = 11, message = "O CPF deve possuir 11 caracteres") String cpf, String creditCard,
					String password) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.creditCard = creditCard;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique=true, name = "id")
	private Integer id;
	
	@Column(nullable = false)
	@Size(min=3, message="O nome deve ter pelo menos 3 caracteres")
	private String name;
	
	@Column(nullable = false)
	@Size(min=11, max = 11, message="O CPF deve possuir 11 caracteres")
	private String cpf;
	
	@Column(nullable = false)
	private String creditCard;
	
	@Column(nullable = false)
	@Size(min=6, message="A senha deve possuir ao menos 6 caracteres")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id", referencedColumnName = "id")
	private Address deliveryAddress;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", cpf=" + cpf + ", creditCard=" + creditCard + ", address="
				+ address + ", deliveryAddress=" + deliveryAddress + "]";
	}
}
