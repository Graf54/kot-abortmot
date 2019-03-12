package org.abormot.impl.db.service

import org.abormot.impl.db.entity.standard.Files
import org.abormot.impl.db.entity.standard.TFile
import org.abormot.impl.db.repository.standard.FilesListRepo
import org.abormot.impl.db.repository.standard.TFileRepo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TFileService(val tFileRepo: TFileRepo, val tFileListRepo: FilesListRepo) {

    fun save(tFile: TFile) {
        tFileRepo.save(tFile)
    }

    fun save(files: Files?) {
        if (files == null) {
            return
        }
        if (files.tFileList != null) {
            for (tFile in files.tFileList!!) {
                save(tFile)
            }
        }

        tFileListRepo.save(files)
    }

    fun addTFile(files: Files?, tFile: TFile): Files {
        var fileList = files
        if (fileList == null) {
            fileList = Files()
        }
        if (fileList.tFileList == null) {
            fileList.tFileList = ArrayList()
        }
        fileList.tFileList!!.add(tFile)
        save(tFile)
        save(fileList)
        return fileList
    }

    fun delete(tFileList: Files) {
        tFileListRepo.delete(tFileList)
    }
}
