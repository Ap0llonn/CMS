package cegep.management.system.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "personId")
    private Person user;

    private String address;
    private String city;
    private String province;
    private String postalCode;

    public Address(Person user, String address, String city, String province, String postalCode) {
        this.user = user;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }
}
