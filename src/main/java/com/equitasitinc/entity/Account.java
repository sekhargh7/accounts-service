package com.equitasitinc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account extends BaseEntity {

	@Column(name = "account_id")
	@Id
	@GeneratedValue
	private Long accountId;

	@Column(name = "name")
	private String name;

	@Column(name = "amount")
	private Float amount;

}
