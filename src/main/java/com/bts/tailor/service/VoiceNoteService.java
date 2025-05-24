package com.bts.tailor.service;


import com.bts.tailor.model.VoiceNote;
import com.bts.tailor.repo.VoiceNoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.core.io.InputStreamResource;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoiceNoteService {

    private final VoiceNoteRepository repository;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    public VoiceNoteService(VoiceNoteRepository repository) {
        this.repository = repository;
    }

    // Upload file to Supabase Storage
    public String uploadFile(InputStream inputStream, String originalFileName, long contentLength) {
        String uniqueFileName = UUID.randomUUID() + "-" + originalFileName;
        String uploadUrl = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucketName, uniqueFileName);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.set("apikey", supabaseKey);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(contentLength);

        HttpEntity<InputStreamResource> requestEntity = new HttpEntity<>(new InputStreamResource(inputStream), headers);
        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.PUT, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            String fileUrl = supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + uniqueFileName;

            // Save metadata in DB
            VoiceNote voiceNote = new VoiceNote();
            voiceNote.setFileName(uniqueFileName);
            voiceNote.setFileUrl(fileUrl);
            voiceNote.setUploadedAt(LocalDateTime.now());
            repository.save(voiceNote);

            return fileUrl;
        } else {
            throw new RuntimeException("Failed to upload file to Supabase: " + response.getBody());
        }
    }

    // Get all voice notes with public URLs
    public List<VoiceNote> getAllVoiceNotes() {
        List<VoiceNote> notes = repository.findAll();

        // Construct public URLs manually to avoid mistakes
        notes.forEach(note -> {
            String publicUrl = String.format("%s/storage/v1/object/public/%s/%s",
                    supabaseUrl, bucketName, note.getFileName());
            note.setFileUrl(publicUrl);
        });

        return notes;
    }
}
