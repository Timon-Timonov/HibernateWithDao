package it.academy.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "people")
@ToString(exclude = "people")
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "street")
    private String street;

    @Column(name = "house")
    private int house;

    @OneToMany(mappedBy = "address",
        fetch = FetchType.EAGER)
    private Set<People> people = new HashSet<>();
}
