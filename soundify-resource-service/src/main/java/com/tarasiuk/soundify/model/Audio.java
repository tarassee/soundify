package com.tarasiuk.soundify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String s3Key;

    @Column
    private String name;

    @Column
    private String format;

    @Column
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

}
