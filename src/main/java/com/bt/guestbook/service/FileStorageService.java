package com.bt.guestbook.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

@Service
public class FileStorageService {

  @Value("${imgur.client.id}")
  private String clientID;

  public String uploadImage(byte[] byteImage) throws IOException {

    URL url;
    url = new URL("https://api.imgur.com/3/image");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    String dataImage = new String(Base64.getEncoder().encode(byteImage));
    String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(dataImage, "UTF-8");

    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "Client-ID " + clientID);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    conn.connect();
    StringBuilder stb = new StringBuilder();
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(data);
    wr.flush();

    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
      stb.append(line).append("\n");
    }
    wr.close();
    rd.close();
    ObjectMapper mapper = new ObjectMapper();
    JsonNode node = mapper.readTree(stb.toString());
    return node.get("data") != null && node.get("data").get("link") != null
        ? node.get("data").get("link").asText()
        : null;
  }
}
