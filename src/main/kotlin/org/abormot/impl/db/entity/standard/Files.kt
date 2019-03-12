package org.abormot.impl.db.entity.standard

import javax.persistence.*

@Entity
class Files {
    @Id
    @GeneratedValue
    var id: Int = 0
    @OneToMany
    @JoinTable(name = "TFILE_LIST_SUPPORT")
    var tFileList: MutableList<TFile>? = null
}
