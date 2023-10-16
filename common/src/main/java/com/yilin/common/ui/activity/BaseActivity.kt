package com.yilin.common.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.yilin.common.R

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    companion object {
        private const val TAG = "BaseActivity"
    }

    protected val binding: T by lazy { getViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initLayoutView()
        initOnClick()
        initViewData()
        setStatusBar()
        Log.i(TAG, "class on onCreate " + this.javaClass.simpleName)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "class on onStart " + this.javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "class on onResume " + this.javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "class on onPause " + this.javaClass.simpleName)
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "class on onRestart " + this.javaClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "class on onStop " + this.javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "class on onDestroy " + this.javaClass.simpleName)
    }


    private fun setStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, binding.root).isAppearanceLightStatusBars = true
    }

    protected abstract fun getViewBinding(): T

    protected abstract fun initLayoutView()

    protected abstract fun initViewData()

    protected open fun initOnClick() {}

    override fun finish() {
        super.finish()
        Log.i(TAG, "class on finish " + this.javaClass.simpleName)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        Log.i(TAG, "class on startActivity " + this.javaClass.simpleName)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    /**
     * 重写 getResource 方法，防止系统字体影响
     */

    override fun getResources(): Resources {
        val res = super.getResources()
        res?.let {
            if (it.configuration.fontScale != 1.0f) {
                val configuration = res.configuration
                configuration.fontScale = 1.0f
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    val context = createConfigurationContext(configuration)
//                    return context.resources
//                } else {
                res.updateConfiguration(configuration, res.displayMetrics)
//                }
            }
        }
        return res
    }

    protected inline fun <reified VM : ViewModel> getViewModel(): VM {
        return ViewModelProvider(this)[VM::class.java]
    }
}