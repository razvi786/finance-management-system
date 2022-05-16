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
@Table(name = "approver_level")
@Entity
public class ApproverLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "approver_level_id")
  private int approverLevelId;

  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne
  @JoinColumn(name = "approver_id", nullable = false)
  private User approver;

  @Column(name = "level")
  private int level;

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
