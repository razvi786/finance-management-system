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
@Table(name = "project")
@Entity
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "project_id")
  private int projectId;

  @Column(name = "user_id")
  private int userId;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "budget")
  private String budget;

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
