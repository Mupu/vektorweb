package me.mupu.vektorweb.persistence.dao;


import lombok.Data;

import javax.persistence.*;

@Data
abstract class BaseEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

}
