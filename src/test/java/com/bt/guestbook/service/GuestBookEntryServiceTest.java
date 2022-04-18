package com.bt.guestbook.service;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.GuestBookEntry;
import com.bt.guestbook.model.User;
import com.bt.guestbook.repo.GuestBookRepository;
import com.bt.guestbook.repo.UserRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestBookEntryServiceTest {

  @Mock private GuestBookRepository guestBookRepository;

  @Mock private UserRepository userRepository;

  @Mock private ModelMapper modelMapper;

  @Mock private FileStorageService fileStorageService;

  @InjectMocks private GuestBookEntryService guestBookEntryService;

  @Test
  void create_entry_success() throws DataFormatException {
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setContent("Test Data");
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    when(modelMapper.map(guestBookEntryDto, GuestBookEntry.class)).thenReturn(guestBookEntry);
    User user = new User();
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    when(guestBookRepository.saveAndFlush(any())).thenReturn(guestBookEntry);
    when(modelMapper.map(guestBookEntry, GuestBookEntryDto.class)).thenReturn(guestBookEntryDto);
    assertEquals(guestBookEntryService.create(guestBookEntryDto, "Guest"), guestBookEntryDto);
  }

  @Test
  void create_entry_fail() {
    DataFormatException dataFormatException =
        assertThrows(
            DataFormatException.class,
            () -> guestBookEntryService.create(null, "Guest"),
            "Invalid data");
    assertTrue(dataFormatException.getMessage().contains("Data invalid"));
  }

  @Test
  void get_all_entries_which_are_not_rejected() {
    GuestBookEntry rejectedEntry = new GuestBookEntry();
    rejectedEntry.setContent("Rejected");
    rejectedEntry.setRejected(true);
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    guestBookEntry.setContent("valid");
    guestBookEntry.setRejected(false);
    List<GuestBookEntry> guestBookEntries = new ArrayList<>();
    guestBookEntries.add(rejectedEntry);
    guestBookEntries.add(guestBookEntry);
    when(guestBookRepository.findAll()).thenReturn(guestBookEntries);
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setRejected(guestBookEntry.isRejected());
    when(modelMapper.map(guestBookEntry, GuestBookEntryDto.class)).thenReturn(guestBookEntryDto);
    List<GuestBookEntryDto> guestBookEntryDtos = guestBookEntryService.getAllEntries();
    assertFalse(guestBookEntryDtos.isEmpty());
    assertEquals(1, guestBookEntryDtos.size());
    assertFalse(guestBookEntryDtos.get(0).isRejected());
  }

  @Test
  void get_all_entries_expect_no_entries() {
    assertEquals(null, guestBookEntryService.getAllEntries());
  }

  @Test
  void approve_success() throws NotFoundException {
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    when(guestBookRepository.findById(any())).thenReturn(java.util.Optional.of(guestBookEntry));
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setApproved(true);
    when(guestBookRepository.saveAndFlush(guestBookEntry)).thenReturn(guestBookEntry);
    when(modelMapper.map(guestBookEntry, GuestBookEntryDto.class)).thenReturn(guestBookEntryDto);
    assertEquals(guestBookEntryDto, guestBookEntryService.approveOrRejectEntry(guestBookEntryDto));
  }

  @Test
  void approve_failure_when_no_record_present() throws NotFoundException {
    NotFoundException notFoundException =
        assertThrows(
            NotFoundException.class,
            () -> guestBookEntryService.approveOrRejectEntry(new GuestBookEntryDto()),
            "Invalid data");
    assertTrue(notFoundException.getMessage().contains("No data found"));
  }

  @Test
  void update_file_success() throws IOException, DataFormatException, NotFoundException {
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setId(1);
    MultipartFile file = new MockMultipartFile("image", new byte[] {0, 1, 0, 1});
    when(guestBookRepository.findById(any()))
        .thenReturn(java.util.Optional.of(new GuestBookEntry()));
    when(fileStorageService.uploadImage(any())).thenReturn("https://imageurl.com");
    when(guestBookRepository.saveAndFlush(any())).thenReturn(new GuestBookEntry());
    when(fileStorageService.prepareImageForUpload(any())).thenReturn(new ByteArrayOutputStream());
    when(modelMapper.map(new GuestBookEntry(), GuestBookEntryDto.class))
        .thenReturn(guestBookEntryDto);
    assertEquals(
        guestBookEntryDto,
        guestBookEntryService.createOrUpdateEntry(new User(), guestBookEntryDto, file));
  }

  @Test
  void update_text_success() throws IOException, DataFormatException, NotFoundException {
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setId(1);
    MultipartFile file = new MockMultipartFile("testName", (byte[]) null);
    when(guestBookRepository.findById(any()))
        .thenReturn(java.util.Optional.of(new GuestBookEntry()));
    when(guestBookRepository.saveAndFlush(any())).thenReturn(new GuestBookEntry());
    when(modelMapper.map(new GuestBookEntry(), GuestBookEntryDto.class))
        .thenReturn(guestBookEntryDto);
    assertEquals(
        guestBookEntryDto,
        guestBookEntryService.createOrUpdateEntry(new User(), guestBookEntryDto, file));
  }

  @Test
  void upload_file_success() throws IOException, DataFormatException, NotFoundException {
    GuestBookEntryDto guestBookEntryDto = new GuestBookEntryDto();
    guestBookEntryDto.setId(0);
    MultipartFile file = new MockMultipartFile("image", new byte[] {0, 1, 0, 1});
    when(fileStorageService.uploadImage(any())).thenReturn("https://imageurl.com");
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    when(guestBookRepository.saveAndFlush(any())).thenReturn(guestBookEntry);
    when(fileStorageService.prepareImageForUpload(any())).thenReturn(new ByteArrayOutputStream());
    User user = new User();
    when(modelMapper.map(guestBookEntry, GuestBookEntryDto.class)).thenReturn(guestBookEntryDto);
    assertEquals(
        guestBookEntryDto,
        guestBookEntryService.createOrUpdateEntry(user, guestBookEntryDto, file));
  }
}
