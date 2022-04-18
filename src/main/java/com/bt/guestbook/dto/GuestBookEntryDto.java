package com.bt.guestbook.dto;

import lombok.Data;

@Data
public class GuestBookEntryDto implements BaseDto {

  private long id;
  private String content;
  private boolean image;
  private boolean approved;
  private boolean rejected;
  private UserDto user;
}
