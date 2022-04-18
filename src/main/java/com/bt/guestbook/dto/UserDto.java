package com.bt.guestbook.dto;

import lombok.Data;

@Data
public class UserDto implements BaseDto {

  private long id;
  private String username;
  private String emailId;
  private int active;
  private RoleDto role;
  private GuestBookEntryDto guestBook;
}
