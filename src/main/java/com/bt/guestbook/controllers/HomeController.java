package com.bt.guestbook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.bt.guestbook.util.GuestBookConstant.*;

@RequestMapping(ROOT)
public interface HomeController {

  @GetMapping(INDEX)
  String index();

  @GetMapping(LOGIN)
  String login();
}
