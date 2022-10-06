package com.brainnit.test.utils

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(applicationContext)

    }
}