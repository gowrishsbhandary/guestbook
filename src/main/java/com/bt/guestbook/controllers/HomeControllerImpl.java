package com.bt.guestbook.controllers;

import org.springframework.stereotype.Controller;

import static com.bt.guestbook.util.GuestBookConstant.INDEX;
import static com.bt.guestbook.util.GuestBookConstant.LOGIN;

@Controller
public class HomeControllerImpl implements HomeController {

  @Override
  public String index() {
    return INDEX;
  }

  @Override
  public String login() {
    return LOGIN;
  }
}
