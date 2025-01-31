package com.example.tradestat

import android.app.Application
import android.content.Context
import android.provider.Settings
import com.my.tracker.MyTracker
import dagger.hilt.android.HiltAndroidApp
import java.net.NetworkInterface
import java.util.Collections

const val SDK_KEY = "25621495456215790334"
@HiltAndroidApp
class App:Application(){
    override fun onCreate() {
        super.onCreate()

        val trackerParams = MyTracker.getTrackerParams()

        // Настройте передачу дополнительных идентификаторов
        trackerParams.setCustomParam("android_id", getAndroidId(applicationContext))
        trackerParams.setCustomParam("mac", getMac())
        val trackerConfig = MyTracker.getTrackerConfig()
        // Инициализируйте трекер
        MyTracker.initTracker(SDK_KEY, this)
    }


    private fun getAndroidId(context: Context): String?
    {
        try
        {
            val cr = context.contentResolver
            if (cr != null)
            {
                return Settings.Secure.getString(cr, Settings.Secure.ANDROID_ID)
            }
        } catch (e: Throwable)
        {
            println(e.message)
        }
        return null
    }
    private fun getMac(): String?
    {
        try
        {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all)
            {
                if (!nif.name.equals("wlan0", ignoreCase = true))
                {
                    continue
                }
                try
                {
                    val macBytes = nif.hardwareAddress ?: return null
                    val result = StringBuilder()
                    for (b in macBytes)
                    {
                        result.append(String.format("%02X:", b))
                    }
                    val length = result.length
                    if (length > 0)
                    {
                        result.deleteCharAt(length - 1)
                    }
                    return result.toString()
                }
                catch (e: Throwable)
                {
                    println(e.message)
                }
            }
        }
        catch (e: Throwable)
        {
            println(e.message)
        }
        return null
    }
}