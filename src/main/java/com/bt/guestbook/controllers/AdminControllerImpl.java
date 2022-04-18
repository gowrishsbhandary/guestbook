package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.service.GuestBookEntryService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.bt.guestbook.util.GuestBookConstant.ADMIN;
import static com.bt.guestbook.util.GuestBookConstant.REDIRECT_ADMIN;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminControllerImpl implements AdminController {

  private final GuestBookEntryService guestBookEntryService;

  public String getAllEntries(Model model) {
    log.info("AdminController: getAllEntries - Start");
    List<GuestBookEntryDto> guestBookEntries = guestBookEntryService.getAllEntries();
    model.addAttribute("guestBookEntries", guestBookEntries);
    return ADMIN;
  }

  public String approveOrRejectEntry(
      @ModelAttribute(value = "guestBookEntryDto") GuestBookEntryDto guestBookEntryDto,
      Model model,
      RedirectAttributes redirectAttributes)
      throws NotFoundException {

    log.info("AdminController: approveEntry - Start");
    GuestBookEntryDto guestBookEntry =
        guestBookEntryService.approveOrRejectEntry(guestBookEntryDto);
    List<GuestBookEntryDto> guestBookEntries = guestBookEntryService.getAllEntries();
    model.addAttribute("guestBookEntries", guestBookEntries);
    if (guestBookEntry.isApproved()) {
      redirectAttributes.addFlashAttribute("approved", "Entry approved successfully!");
    }
    if (guestBookEntry.isRejected()) {
      redirectAttributes.addFlashAttribute("rejected", "Entry deleted successfully!");
    }

    return REDIRECT_ADMIN;
  }
}
