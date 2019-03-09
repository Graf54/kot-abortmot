package org.abormot.db.service

import org.abormot.db.entity.standard.Link
import org.abormot.db.entity.standard.LinkList
import org.abormot.db.repository.standard.LinkListRepo
import org.abormot.db.repository.standard.LinkRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LinkService(val linkRepo: LinkRepo, val linkListRepo: LinkListRepo) {


    fun save(link: Link) {
        linkRepo.save(link)
    }

    fun delete(link: Link) {
        linkRepo.delete(link)
    }

    fun delete(linkList: LinkList) {
        linkListRepo.delete(linkList)
    }

    fun getLinkList(id: Int): LinkList {
        return linkListRepo.findById(id).get()
    }

    fun save(linkList: LinkList) {
        linkListRepo.save(linkList)
    }
}
