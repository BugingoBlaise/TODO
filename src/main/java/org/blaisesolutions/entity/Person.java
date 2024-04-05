package org.blaisesolutions.entity;


import lombok.*;

import javax.persistence.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "my_table")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use an appropriate strategy
    private Long id;

    private String username;
    private Integer age;
    @Lob
    private byte[] imageData;

    private String imagePath; // St
    private boolean deleted = false;


}
