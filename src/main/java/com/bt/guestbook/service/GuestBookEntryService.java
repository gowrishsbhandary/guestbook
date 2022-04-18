package com.bt.guestbook.service;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.GuestBookEntry;
import com.bt.guestbook.model.User;
import com.bt.guestbook.repo.GuestBookRepository;
import com.bt.guestbook.repo.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestBookEntryService {

  private final GuestBookRepository guestBookRepository;

  private final UserRepository userRepository;

  private final ModelMapper modelMapper;

  private final FileStorageService fileStorageService;

  public GuestBookEntryDto create(GuestBookEntryDto guestBookEntryDto, String username)
      throws DataFormatException {
    if (guestBookEntryDto == null || guestBookEntryDto.getContent() == null)
      throw new DataFormatException("Data invalid");
    GuestBookEntry guestBookEntry = convertToEntity(guestBookEntryDto);
    User user = userRepository.findByUsername(username);
    guestBookEntry.setUser(user);
    return convertToDto(guestBookRepository.saveAndFlush(guestBookEntry));
  }

  public List<GuestBookEntryDto> getAllEntries() {
    List<GuestBookEntry> guestBookEntryEntries = guestBookRepository.findAll();
    return guestBookEntryEntries.isEmpty()
        ? null
        : guestBookEntryEntries.stream()
            .filter(entry -> Boolean.FALSE.equals(entry.isRejected()))
            .map(this::convertToDto)
            .collect(Collectors.toList());
  }

  public GuestBookEntryDto approveOrRejectEntry(GuestBookEntryDto guestBookEntryDto)
      throws NotFoundException {
    Optional<GuestBookEntry> optionalGuestBook =
        guestBookRepository.findById(guestBookEntryDto.getId());
    if (!optionalGuestBook.isPresent()) throw new NotFoundException("No data found");
    GuestBookEntry guestBookEntry = optionalGuestBook.get();
    if (guestBookEntryDto.isApproved()) {
      guestBookEntry.setApproved(guestBookEntryDto.isApproved());
    }
    if (guestBookEntryDto.isRejected()) {
      guestBookEntry.setRejected(guestBookEntryDto.isRejected());
    }
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  private GuestBookEntryDto uploadImage(MultipartFile multipartFile, String username)
      throws IOException {
    String url =
        fileStorageService.uploadImage(
            fileStorageService.prepareImageForUpload(multipartFile).toByteArray());
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    guestBookEntry.setContent(url);
    guestBookEntry.setImage(true);
    User user = userRepository.findByUsername(username);
    guestBookEntry.setUser(user);
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  private GuestBookEntryDto update(GuestBookEntryDto guestBookEntryDto) throws NotFoundException {
    Optional<GuestBookEntry> optionalGuestBook =
        guestBookRepository.findById(guestBookEntryDto.getId());
    if (!optionalGuestBook.isPresent()) throw new NotFoundException("No data found");

    GuestBookEntry guestBookEntry = optionalGuestBook.get();
    guestBookEntry.setContent(guestBookEntryDto.getContent());
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  private GuestBookEntryDto updateImage(MultipartFile multipartFile, long entryID)
      throws IOException, NotFoundException {
    Optional<GuestBookEntry> optionalGuestBookEntry = guestBookRepository.findById(entryID);
    if (!optionalGuestBookEntry.isPresent()) throw new NotFoundException("No data found");
    String url =
        fileStorageService.uploadImage(
            fileStorageService.prepareImageForUpload(multipartFile).toByteArray());
    GuestBookEntry guestBookEntry = optionalGuestBookEntry.get();
    guestBookEntry.setContent(url);
    guestBookEntry.setImage(true);
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  public GuestBookEntryDto createOrUpdateEntry(
      User user, GuestBookEntryDto guestBookEntryDto, MultipartFile file)
      throws IOException, NotFoundException, DataFormatException {
    GuestBookEntryDto guestBookEntry;
    if (guestBookEntryDto.getId() > 0) {
      log.info("Update an entry...... !");
      guestBookEntry =
          !file.isEmpty()
              ? updateImage(file, guestBookEntryDto.getId())
              : update(guestBookEntryDto);

    } else {
      log.info("Add an entry...... !");
      String userName = user.getUsername();
      guestBookEntry =
          !file.isEmpty() ? uploadImage(file, userName) : create(guestBookEntryDto, userName);
    }
    return guestBookEntry;
  }

  private GuestBookEntryDto convertToDto(GuestBookEntry guestBookEntry) {
    return modelMapper.map(guestBookEntry, GuestBookEntryDto.class);
  }

  private GuestBookEntry convertToEntity(GuestBookEntryDto guestBookEntryDto) {
    return modelMapper.map(guestBookEntryDto, GuestBookEntry.class);
  }
}
