package com.fms.ems.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name = "role_permission")
@Entity
public class RolePermission {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "role_permission_id")
	private int rolePermissionId;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role roleId;
	
	@ManyToOne
	@JoinColumn(name = "permission_id")
	private Permission permissionId;
	
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
