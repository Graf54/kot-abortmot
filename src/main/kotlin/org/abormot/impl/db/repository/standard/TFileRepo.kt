package org.abormot.impl.db.repository.standard

import org.abormot.impl.db.entity.standard.TFile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TFileRepo : CrudRepository<TFile, Int>
