package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI
import com.example.background.R

class BlurWorker(ctx:Context,params:WorkerParameters):Worker(ctx,params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        makeStatusNotification("Blurring Image",appContext)
        val resourceUri = inputData.getString(KEY_IMAGE_URI)
        sleep()
        return try {
            val resolver = appContext.contentResolver
            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
            val output = blurBitmap(picture,appContext)
            val outputUri = writeBitmapToFile(appContext,output)
            makeStatusNotification("Output is $outputUri",appContext)
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            return Result.success(outputData)
        }catch (throwable:Throwable){
            Result.failure()
        }

    }
}