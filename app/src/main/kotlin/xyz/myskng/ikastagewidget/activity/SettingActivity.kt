package xyz.myskng.ikastagewidget.activity

import android.os.Bundle
import android.app.Activity
import android.preference.PreferenceFragment
import xyz.myskng.ikastagewidget.R

class SettingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        fragmentManager.beginTransaction().replace(android.R.id.content,PrefActivity()).commit()
    }
    companion object{
        class PrefActivity : PreferenceFragment() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                addPreferencesFromResource(R.xml.pref_screen)
            }
        }
    }
}
