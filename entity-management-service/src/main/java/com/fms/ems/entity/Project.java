package com.fms.ems.entity;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "project")
@Entity
@ToString(exclude = "approverLevels")
@JsonIgnoreProperties(value = "approverLevels")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "project_id")
  private int projectId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToMany(mappedBy = "project")
  private List<ApproverLevel> approverLevels;

  @Column(name = "name")
  private String projectName;

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
