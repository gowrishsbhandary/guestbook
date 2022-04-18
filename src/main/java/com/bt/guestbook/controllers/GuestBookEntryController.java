package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.zip.DataFormatException;

@RequestMapping()
public interface GuestBookEntryController {

  @GetMapping("guestBook")
  String guestBook(Model model, @ModelAttribute(value = "entryId") String entryId);

  @PostMapping("entry")
  String addEntry(
      @AuthenticationPrincipal User user,
      GuestBookEntryDto guestBookEntryDto,
      @RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes,
      Model model)
      throws IllegalAccessException, DataFormatException;

  @PostMapping("updateEntry")
  String updateEntry(
      @AuthenticationPrincipal User user,
      GuestBookEntryDto guestBookEntryDto,
      @RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes,
      Model model)
      throws IllegalAccessException, DataFormatException;
}
