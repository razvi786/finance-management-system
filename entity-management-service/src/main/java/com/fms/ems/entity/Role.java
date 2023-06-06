package com.fms.ems.entity;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "role")
@Entity
@ToString
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "role_id")
  private int roleId;

  @Column(name = "name")
  private String roleName;

  @ManyToMany
  @JoinTable(
      name = "role_permission",
      joinColumns = @JoinColumn(name = "role_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private List<Permission> permissions;

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
