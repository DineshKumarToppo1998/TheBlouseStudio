package com.bts.tailor.controller;


import com.bts.tailor.model.VoiceNote;
import com.bts.tailor.service.VoiceNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/voice-notes")
public class VoiceNoteController {

    private final VoiceNoteService service;

    public VoiceNoteController(VoiceNoteService service) {
        this.service = service;
    }

    /**
     * @apiNote Upload a voice note - Customer Facing endpoint
     * @param file
     * @return ResponseEntity
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadVoiceNote(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String originalFileName = file.getOriginalFilename();
            long contentLength = file.getSize();
            String fileUrl = service.uploadFile(inputStream, originalFileName, contentLength);
            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error uploading file: " + e.getMessage());
        }
    }

    /**
     * @apiNote Admin Facing Endpoint
     * @return ResponseEntity
     */
    @GetMapping("/fetch")
    public ResponseEntity<List<VoiceNote>> getAllVoiceNotes() {
        List<VoiceNote> notes = service.getAllVoiceNotes();
        return ResponseEntity.ok(notes);
    }
}
