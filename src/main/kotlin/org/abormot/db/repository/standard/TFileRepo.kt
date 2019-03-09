package org.abormot.db.repository.standard

import org.abormot.db.entity.standard.TFile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TFileRepo : CrudRepository<TFile, Int>
