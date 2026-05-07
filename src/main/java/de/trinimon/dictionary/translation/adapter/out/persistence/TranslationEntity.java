package de.trinimon.dictionary.translation.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRANSLATION", schema = "common")
public class TranslationEntity {

    @Id
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "target")
    private String target;

}
