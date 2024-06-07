package cegep.management.system.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cegep.management.system.api.model.Session;
import cegep.management.system.api.service.SessionService;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<List<Session>> getAllSessions() {
        List<Session> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Session> getSessionById(@PathVariable Long sessionId) {
        return sessionService.getSessionById(sessionId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<Session> updateSession(@PathVariable Long sessionId, @RequestBody Session updatedSession) {
        return sessionService.getSessionById(sessionId)
                .map(session -> {
                    Session updatedSessionEntity = sessionService.updateSession(sessionId, updatedSession);
                    return ResponseEntity.ok(updatedSessionEntity);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/current")
    public ResponseEntity<Session> getCurrentSession() {
        Session currentSession = sessionService.getOrCreateSessionForCurrentDate();
        return ResponseEntity.ok(currentSession);
    }
}