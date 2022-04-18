package com.bt.guestbook.service;

import com.bt.guestbook.dto.GuestBookEntryDto;
import com.bt.guestbook.model.GuestBookEntry;
import com.bt.guestbook.model.User;
import com.bt.guestbook.repo.GuestBookRepository;
import com.bt.guestbook.repo.UserRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
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
    guestBookEntry = this.guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
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

  public void approveOrRejectEntry(GuestBookEntryDto guestBookEntryDto) throws NotFoundException {
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
    convertToDto(guestBookEntry);
  }

  public GuestBookEntryDto uploadImage(MultipartFile multipartFile, String username)
      throws IOException {
    File file = convertMultiPartToFile(multipartFile);
    BufferedImage image = ImageIO.read(file);
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    ImageIO.write(image, "png", byteArray);
    String url = fileStorageService.uploadImage(byteArray.toByteArray());
    GuestBookEntry guestBookEntry = new GuestBookEntry();
    guestBookEntry.setContent(url);
    guestBookEntry.setImage(true);
    User user = userRepository.findByUsername(username);
    guestBookEntry.setUser(user);
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  public void update(GuestBookEntryDto guestBookEntryDto) throws DataFormatException {
    if (guestBookEntryDto == null
        || guestBookEntryDto.getId() < 0
        || guestBookEntryDto.getContent() == null) throw new DataFormatException("Data invalid");
    GuestBookEntry guestBookEntry = convertToEntity(guestBookEntryDto);
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    convertToDto(guestBookEntry);
  }

  public GuestBookEntryDto updateImage(MultipartFile multipartFile, long entryID)
      throws IOException, NotFoundException {
    Optional<GuestBookEntry> optionalGuestBookEntry = guestBookRepository.findById(entryID);
    if (!optionalGuestBookEntry.isPresent()) throw new NotFoundException("No data found");
    File file = convertMultiPartToFile(multipartFile);
    BufferedImage image = ImageIO.read(file);
    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
    ImageIO.write(image, "png", byteArray);
    String url = fileStorageService.uploadImage(byteArray.toByteArray());
    GuestBookEntry guestBookEntry = optionalGuestBookEntry.get();
    guestBookEntry.setContent(url);
    guestBookEntry.setImage(true);
    guestBookEntry = guestBookRepository.saveAndFlush(guestBookEntry);
    return convertToDto(guestBookEntry);
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream fos = new FileOutputStream(convFile)) {
      fos.write(file.getBytes());
    }
    return convFile;
  }

  private GuestBookEntryDto convertToDto(GuestBookEntry guestBookEntry) {
    return modelMapper.map(guestBookEntry, GuestBookEntryDto.class);
  }

  private GuestBookEntry convertToEntity(GuestBookEntryDto guestBookEntryDto) {
    return modelMapper.map(guestBookEntryDto, GuestBookEntry.class);
  }
}
