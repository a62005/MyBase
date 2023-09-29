package com.yilin.mybase.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.yilin.mybase.R

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    companion object {
        private const val TAG = "BaseActivity"
    }

    protected val binding: T by lazy { getViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
        setContentView(binding.root)
        initLayoutView()
        initOnClick()
        initViewData()
        setStatusBar()
        Log.i(TAG, "class on create " + this.javaClass.simpleName)
    }

    private fun setStatusBar() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, binding.root).isAppearanceLightStatusBars = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    protected abstract fun getViewBinding(): T


    protected abstract fun initLayoutView()

    protected abstract fun initViewData()

    protected fun initOnClick() {}

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "class on onResume " + this.javaClass.simpleName)
    }

    override fun finish() {
        super.finish()
        Log.i(TAG, "class on finish " + this.javaClass.simpleName)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "class on onDestroy " + this.javaClass.simpleName)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
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
}