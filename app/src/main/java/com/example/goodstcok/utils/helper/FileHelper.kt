package com.example.goodstcok.utils.helper

import android.content.Context
import java.io.File
import java.io.IOException

@Throws(IOException::class)
public fun createFolderCacheCameraJPG(context: Context, nameFile: String): File {
    val folderCacheImage =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/cache_image/")
    if (!folderCacheImage.exists()) {
        folderCacheImage.mkdirs()
    }
    val folderCacheImageCompress =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/cache_image_compress/")
    if (!folderCacheImageCompress.exists()) {
        folderCacheImageCompress.mkdirs()
    }
    return File(context.getExternalFilesDir(null)!!.absolutePath + "/cache_image/" + nameFile + ".jpg/")
}
