package org.abormot.db.entity.standard

import javax.persistence.*


@Entity
class TFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    @Column(length = 500)//todo возможно больше нужно - следует уточнить
    var link: String = ""
    var typeId: Int = 0
    var name: String = ""
}
