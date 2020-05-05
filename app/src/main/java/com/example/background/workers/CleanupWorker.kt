package com.example.background.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.background.OUTPUT_PATH
import java.io.File
import java.lang.Exception

class CleanupWorker(ctx : Context, params:WorkerParameters):Worker(ctx,params) {
    override fun doWork(): Result {
        makeStatusNotification("Cleaning up old temporary files",applicationContext)
        sleep()
        return try{
            val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDir.exists()){
                val entries = outputDir.listFiles()
                if (entries != null){
                    for(entry in entries){
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")){
                            val deleted = entry.delete()
                        }
                    }
                }
            }
            Result.success()
        }catch (e:Exception){
            Result.failure()
        }
    }
}