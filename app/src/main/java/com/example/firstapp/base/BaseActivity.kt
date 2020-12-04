package com.example.firstapp.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.view.ContextThemeWrapper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.example.firstapp.R
import com.example.firstapp.showToast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*
import kotlin.reflect.KClass

abstract class BaseActivity<ViewModel : BaseViewModel>(
    private var layoutId: Int,
    viewModelClass: KClass<ViewModel>
) : AppCompatActivity() {
    val configuration: Configuration? = null

    val viewModel: ViewModel by lazy { getViewModel<ViewModel>(viewModelClass) }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        loadLocate()

    }
    @Override
    @SuppressWarnings("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(layoutId)
        setupViews()
        setupLiveData()
        setupFetchRequests()
        showError()
        loadLocate()
    }


    fun initLanguage() {
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)
        loadLocate()
        showChangeLang() }



    private fun showError() {

        viewModel.errorMessage.observeForever {
            showToast(it)
        }

    }

    abstract fun setupFetchRequests()


    abstract fun setupViews()

    abstract fun setupLiveData()

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language: String? = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }

    private fun showChangeLang() {

        val listItmes = arrayOf("Espain", "Kyrgyz", "English")

        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->

            if (which == 0) {
                setLocate("es")
                recreate()
            } else if (which == 1) {
                setLocate("ky")
                recreate()
            } else if (which == 2) {
                setLocate("en")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
}