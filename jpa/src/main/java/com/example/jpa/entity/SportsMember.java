package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "locker")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SportsMember {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @OneToOne(optional = false, fetch = FetchType.LAZY) // optional = false / null 없게
    // @OneToOne(optional = false)
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;
}
