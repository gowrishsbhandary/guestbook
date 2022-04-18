package com.bt.guestbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  private Long roleId;

  @Column(name = "role")
  private String roleName;

  public Role(Long roleId, String roleName) {
    this.roleId = roleId;
    this.roleName = roleName;
  }
}
