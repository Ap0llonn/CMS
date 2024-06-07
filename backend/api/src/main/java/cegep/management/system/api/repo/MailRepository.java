package cegep.management.system.api.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cegep.management.system.api.model.Mail;
import cegep.management.system.api.model.Person;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {
    List<Mail> findBySender(Person sender);

    List<Mail> findByReceiver(Person receiver);
}
