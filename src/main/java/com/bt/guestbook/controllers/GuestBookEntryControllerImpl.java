package com.bt.guestbook.controllers;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.User;
import com.bt.guestbook.service.GuestBookEntryService;
import javassist.NotFoundException;
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
  public String entry(
      User user,
      GuestBookEntryDto guestBookEntryDto,
      MultipartFile file,
      RedirectAttributes redirectAttributes,
      Model model) {
    try {
      log.info("Entry begins...... !");
      if (StringUtils.hasText(guestBookEntryDto.getContent()) && !file.isEmpty()) {
        redirectAttributes.addFlashAttribute(ERROR_MULTI_CONTENT_KEY, ERROR_MULTI_CONTENT_VALUE);
        model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntryDto);
      }
      if (!isValidImageType(file)) {
        redirectAttributes.addFlashAttribute(ERROR_INVALID_IMAGE_TYPE_KEY, ERROR_INVALID_IMAGE_TYPE_VALUE);
        model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntryDto);
      } else {
        GuestBookEntryDto guestBookEntry =
            guestBookEntryService.createOrUpdateEntry(user, guestBookEntryDto, file);
        model.addAttribute(GUEST_BOOK_ENTRY_DTO, guestBookEntry);
        redirectAttributes.addFlashAttribute(SUCCESS_KEY, SUCCESS_VALUE);
      }
    } catch (IOException | DataFormatException | NotFoundException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute(ERROR_KEY, ERROR_VALUE);
    }
    return REDIRECT_GUEST_BOOK;
  }

  public boolean isValidImageType(MultipartFile multipartFile) {
    String contentType = multipartFile.getContentType();
    return contentType.equals("image/png")
        || contentType.equals("image/jpg")
        || contentType.equals("image/jpeg");
  }
}
