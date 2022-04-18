package com.bt.guestbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private long id;

  @Column(name = "user_name", nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "email_id")
  private String emailId;

  private int active;
  private LocalDateTime createTime;

  @ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
  @JoinColumn(
      name = "role_id",
      referencedColumnName = "role_id",
      insertable = true,
      updatable = true)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<GuestBookEntry> guestBookEntry;

  public User(User user) {
    this.id = user.getId();
    this.emailId = user.getEmailId();
    this.username = user.getUsername();
    this.password = user.getPassword();
    this.active = user.getActive();
    this.createTime = user.getCreateTime();
    this.role = user.getRole();
    this.guestBookEntry = user.getGuestBookEntry();
  }

  public User(
      String username,
      String password,
      String emailId,
      int active,
      LocalDateTime createTime,
      Role role,
      List<GuestBookEntry> guestBookEntry) {
    this.username = username;
    this.password = password;
    this.emailId = emailId;
    this.active = active;
    this.createTime = createTime;
    this.role = role;
    this.guestBookEntry = guestBookEntry;
  }
}
