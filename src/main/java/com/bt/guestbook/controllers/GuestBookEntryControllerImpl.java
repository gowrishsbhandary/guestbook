package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.User;
import com.bt.guestbook.service.GuestBookEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.zip.DataFormatException;

import static com.bt.guestbook.util.GuestBookConstant.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GuestBookEntryControllerImpl implements GuestBookEntryController {

  private final GuestBookEntryService guestBookEntryService;

  @Override
  public String guestBook(Model model, String entryId) {
    log.info("Getting guest book entry view");
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setId(StringUtils.hasText(entryId) ? Long.parseLong(entryId) : 0);
    model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntryDto);
    return ENDPOINT_GUESTBOOK;
  }

  @Override
  public String addEntry(
      User user,
      GuestBookEntryDto guestBookEntryDto,
      MultipartFile file,
      RedirectAttributes redirectAttributes,
      Model model) {
    try {
      log.info("Adding an entry");
      if (StringUtils.hasText(guestBookEntryDto.getContent()) && !file.isEmpty()) {
        redirectAttributes.addFlashAttribute(ERROR_MULTI_CONTENT_KEY, ERROR_MULTI_CONTENT_VALUE);
        return REDIRECT_GUEST_BOOK;
      }
      GuestBookEntryDto guestBookEntry;
      String userName = user.getUsername();
      if (!file.isEmpty()) {
        guestBookEntry = guestBookEntryService.uploadImage(file, userName);
      } else {
        guestBookEntry = guestBookEntryService.create(guestBookEntryDto, userName);
      }

      model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntry);
      redirectAttributes.addFlashAttribute(SUCCESS_KEY, SUCCESS_VALUE);
    } catch (IOException | DataFormatException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute(ERROR_KEY, ERROR_VALUE);
    }
    return REDIRECT_GUEST_BOOK;
  }

  @Override
  public String updateEntry(
      User user,
      GuestBookEntryDto guestBookEntryDto,
      MultipartFile file,
      RedirectAttributes redirectAttributes,
      Model model) {
    try {
      log.info("Adding an entry");
      if (StringUtils.hasText(guestBookEntryDto.getContent()) && !file.isEmpty()) {
        redirectAttributes.addFlashAttribute(ERROR_MULTI_CONTENT_KEY, ERROR_MULTI_CONTENT_VALUE);
        return REDIRECT_GUEST_BOOK;
      }
      GuestBookEntryDto guestBookEntry;
      String userName = user.getUsername();
      if (!file.isEmpty()) {
        guestBookEntry = guestBookEntryService.uploadImage(file, userName);
      } else {
        guestBookEntry = guestBookEntryService.create(guestBookEntryDto, userName);
      }

      model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntry);
      redirectAttributes.addFlashAttribute(SUCCESS_KEY, SUCCESS_VALUE);
    } catch (IOException | DataFormatException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute(ERROR_KEY, ERROR_VALUE);
    }
    return REDIRECT_GUEST_BOOK;
  }
}
