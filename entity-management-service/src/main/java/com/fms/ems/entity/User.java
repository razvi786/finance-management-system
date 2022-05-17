package com.fms.ems.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Table(name = "user")
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private int userId;

  @OneToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "phone")
  private String phone;

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
