package cegep.management.system.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    private Long receiverId;
    private Long senderId;
    private String subject;
    private String content;
    private Date date;
}
