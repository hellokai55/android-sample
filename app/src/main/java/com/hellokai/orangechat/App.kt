package com.hellokai.orangechat

import android.app.Application
import android.content.Context
import com.hellokai.orangechat.hotfix.HotFixManager

class App: Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        HotFixManager.init(this)
    }

}