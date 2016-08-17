package com.mirzaakhena.batchsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mirzaakhena.batchsystem.model.RawMaterialDetail;
import com.mirzaakhena.batchsystem.model.RawMaterialDetailKey;

public interface RawMaterialDetailDao extends JpaRepository<RawMaterialDetail, RawMaterialDetailKey> {

}
