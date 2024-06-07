package cegep.management.system.api.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person receiver;

    @ManyToOne
    private Person sender;

    private String subject;
    private String content;
    private Date date;
    private boolean read = false;

    public Mail(Person receiver, Person sender, String subject, String content, Date date) {
        this.receiver = receiver;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }
}
