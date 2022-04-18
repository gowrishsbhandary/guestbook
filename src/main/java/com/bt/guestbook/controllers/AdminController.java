package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import javassist.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.bt.guestbook.util.GuestBookConstant.ADMIN;
import static com.bt.guestbook.util.GuestBookConstant.MODIFY;

@RequestMapping()
public interface AdminController {

  @GetMapping(ADMIN)
  String getAllEntries(Model model);

  @PostMapping(MODIFY)
  String approveOrRejectEntry(
      @ModelAttribute(value = "guestBookEntryDto") GuestBookEntryDto guestBookEntryDto,
      Model model,
      RedirectAttributes redirectAttributes)
      throws NotFoundException;
}
