package com.bt.guestbook.util;

public class GuestBookConstant {

  public static final String GUEST_BOOK_ENTRY_DTO = "guestBookEntryDto";
  public static final String ENDPOINT_GUESTBOOK = "guestBook";
  public static final String REDIRECT_GUEST_BOOK = "redirect:/guestBook";
  public static final String REDIRECT_ADMIN = "redirect:admin";
  public static final String ADMIN = "admin";
  public static final String MODIFY = "modify";
  public static final String INDEX = "index";
  public static final String LOGIN = "login";
  public static final String ROOT = "/";

  public static final String ERROR_MULTI_CONTENT_VALUE =
      "Adding both image and text at the same time is not allowed!";
  public static final String ERROR_MULTI_CONTENT_KEY = "multiContent";
  public static final String ERROR_KEY = "error";
  public static final String ERROR_VALUE = "Something went wrong! Please try again.";
  public static final String SUCCESS_KEY = "success";
  public static final String SUCCESS_VALUE = "You have successfully made an entry!";
  public static final String ERROR_INVALID_IMAGE_TYPE_KEY = "invalidImage";
  public static final String ERROR_INVALID_IMAGE_TYPE_VALUE =
      "Allowed image types are jpg, jpeg and png!";

  private GuestBookConstant() {}
}
