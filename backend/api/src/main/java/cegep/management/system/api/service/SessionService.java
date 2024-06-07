package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.AcademicYear;
import cegep.management.system.api.model.Session;
import cegep.management.system.api.repo.SessionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final AcademicYearService academicYearService;

    public SessionService(SessionRepository sessionRepository, AcademicYearService academicYearService) {
        this.sessionRepository = sessionRepository;
        this.academicYearService = academicYearService;
    }

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId);
    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    @Transactional
    public Session updateSession(Long sessionId, Session updatedSession) {
        return sessionRepository.findById(sessionId).map(session -> {
            session.setAcademicYear(updatedSession.getAcademicYear());
            session.setName(updatedSession.getName());
            session.setStartDate(updatedSession.getStartDate());
            session.setEndDate(updatedSession.getEndDate());
            return sessionRepository.save(session);
        }).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    public void deleteSession(Long sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    public Session getOrCreateSessionForCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        AcademicYear currentAcademicYear = academicYearService.getOrCreateNextAcademicYear(currentDate);

        Optional<Session> existingSession = sessionRepository.findSessionByDateAndAcademicYear(currentDate,
                currentAcademicYear);

        if (existingSession.isPresent()) {
            return existingSession.get();
        } else {
            return createSessionForAcademicYear(currentAcademicYear, currentDate);
        }
    }

    private Session createSessionForAcademicYear(AcademicYear academicYear, LocalDate currentDate) {
        LocalDate session1StartDate = academicYear.getStartDate();
        LocalDate session1EndDate = LocalDate.of(session1StartDate.getYear(), 12, 22);
        LocalDate session2StartDate = LocalDate.of(session1EndDate.getYear() + 1, 1, 18);
        LocalDate session2EndDate = LocalDate.of(session2StartDate.getYear(), 5, 26);

        Optional<Session> existingAutumnSession = sessionRepository.findByAcademicYearAndNameAndStartDateAndEndDate(
                academicYear, "Autumn", session1StartDate, session1EndDate);
        Optional<Session> existingWinterSession = sessionRepository.findByAcademicYearAndNameAndStartDateAndEndDate(
                academicYear, "Winter", session2StartDate, session2EndDate);

        if (currentDate.isBefore(session1EndDate)) {
            if (existingAutumnSession.isPresent()) {
                return existingAutumnSession.get();
            } else {
                return sessionRepository.save(new Session(academicYear, "Autumn", session1StartDate, session1EndDate));
            }
        } else {
            if (existingWinterSession.isPresent()) {
                return existingWinterSession.get();
            } else {
                return sessionRepository.save(new Session(academicYear, "Winter", session2StartDate, session2EndDate));
            }
        }
    }
}
