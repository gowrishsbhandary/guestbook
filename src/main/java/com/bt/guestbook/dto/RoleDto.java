package com.bt.guestbook.dto;

import lombok.Data;

@Data
public class RoleDto implements BaseDto {

  private Long roleId;
  private String roleName;
}
