package com.fms.ems.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name = "vendor")
@Entity
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "vendor_id")
	private int vendorId;

	@Column(name = "vendor_name")
	private String vendorName;

	@Column(name = "account_holder_name")
	private String accountHolderName;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "ifsc_code")
	private String ifscCode;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "branch")
	private String branch;

	@Column(name = "upi_id")
	private String upiId;

	@CreationTimestamp
	@Column(name = "created_datetime")
	private OffsetDateTime createdDatetime;

	@UpdateTimestamp
	@Column(name = "updated_datetime")
	private OffsetDateTime updatedDatetime;

	@Version
	@Column(name = "concurrency_version")
	private Integer concurrencyVersion;
}
