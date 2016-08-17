package com.mirzaakhena.batchsystem.model;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseCategory extends SubAccount {


}
