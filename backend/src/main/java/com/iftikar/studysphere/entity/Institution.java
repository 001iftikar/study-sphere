package com.iftikar.studysphere.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
@Accessors(chain = true)
@Table(name = "institution")
public class Institution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String uniqueName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String secretKey;

    @ManyToOne
    @JoinColumn(name = "admin", nullable = false)
    private Admin admin;
}
