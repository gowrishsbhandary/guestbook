package com.bt.guestbook.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "guest_book")
public class GuestBookEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "entry_id")
  private long id;

  @Column(name = "content")
  private String content;

  @Column(name = "image")
  private boolean image;

  @Column(name = "approved")
  private boolean approved;

  @Column(name = "rejected")
  private boolean rejected;

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "user_id",
      insertable = true,
      updatable = false)
  private User user;
}
