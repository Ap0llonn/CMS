package cegep.management.system.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cegep.management.system.api.dto.MailDTO;
import cegep.management.system.api.error.ResourceNotFoundException;
import cegep.management.system.api.model.Mail;
import cegep.management.system.api.model.Person;
import cegep.management.system.api.repo.MailRepository;
import cegep.management.system.api.repo.PersonRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {

    private final MailRepository mailRepository;
    private final PersonRepository personRepository;

    public MailService(MailRepository mailRepository, PersonRepository personRepository) {
        this.mailRepository = mailRepository;
        this.personRepository = personRepository;
    }

    public List<Mail> getAllMails() {
        return mailRepository.findAll();
    }

    public Optional<Mail> getMailById(Long mailId) {
        return mailRepository.findById(mailId);
    }

    public List<Mail> getMailsBySenderId(Long senderId) {
        Person sender = personRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with ID: " + senderId));
        return mailRepository.findBySender(sender);
    }

    public List<Mail> getMailsByReceiverId(Long receiverId) {
        Person receiver = personRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found with ID: " + receiverId));
        return mailRepository.findByReceiver(receiver);
    }

    public Mail createMail(MailDTO mailDTO) {
        Person receiver = personRepository.findById(mailDTO.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Can't find the receiver Id: " + mailDTO.getReceiverId()));
        Person sender = personRepository.findById(mailDTO.getSenderId())
                .orElseThrow(() -> new RuntimeException("Can't find the sender id: " + mailDTO.getSenderId()));
        return mailRepository
                .save(new Mail(receiver, sender, mailDTO.getSubject(), mailDTO.getContent(), mailDTO.getDate()));
    }

    @Transactional
    public Mail updateMail(Long mailId, Mail updatedMail) {
        return mailRepository.findById(mailId).map(mail -> {
            mail.setReceiver(updatedMail.getReceiver());
            mail.setSender(updatedMail.getSender());
            mail.setSubject(updatedMail.getSubject());
            mail.setContent(updatedMail.getContent());
            mail.setDate(updatedMail.getDate());
            mail.setRead(updatedMail.isRead());
            return mailRepository.save(mail);
        }).orElseThrow(() -> new ResourceNotFoundException("Mail not found"));
    }

    public void deleteMail(Long mailId) {
        mailRepository.deleteById(mailId);
    }
}
