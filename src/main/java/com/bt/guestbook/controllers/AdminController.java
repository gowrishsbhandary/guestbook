package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping()
public interface AdminController {

  @GetMapping("admin")
  String getAllEntries(Model model);

  @PostMapping("modify")
  String approveOrRejectEntry(
      @ModelAttribute(value = "guestBookEntryDto") GuestBookEntryDto guestBookEntryDto,
      Model model,
      RedirectAttributes redirectAttributes);
}
