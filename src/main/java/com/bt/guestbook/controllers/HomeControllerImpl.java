package com.bt.guestbook.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class HomeControllerImpl implements HomeController {
  @Override
  public String index() {
    return "index";
  }

  @Override
  public String login() {
    return "login";
  }
}
