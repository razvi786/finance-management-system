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
import lombok.ToString;

@Data
@Table(name = "permission")
@Entity
@ToString
public class Permission {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "permission_id")
	private int permissionId;

	@Column(name = "name")
	private String permissionName;

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
