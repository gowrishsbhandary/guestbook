package com.bt.guestbook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HomeController {
  @GetMapping("index")
  String index();

  @GetMapping("login")
  String login();
}
