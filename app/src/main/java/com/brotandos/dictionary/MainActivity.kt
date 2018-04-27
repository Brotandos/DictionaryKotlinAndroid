package com.brotandos.dictionary

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import org.jetbrains.anko.frameLayout

class MainActivity : AppCompatActivity() {
    private val fragManager = supportFragmentManager
    private val container = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frameLayout { id = container }
        // If there are any instances saved, return
        if (savedInstanceState != null) return
        // else run default fragment
        changeFragment(DictionaryFragment())
    }


    @SuppressLint("PrivateResource")
    private fun changeFragment(f: Fragment, needToCleanStack: Boolean = false) {
        if (needToCleanStack) clearBackStack()
        fragManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.abc_fade_in,
                        R.anim.abc_fade_out,
                        R.anim.abc_popup_enter,
                        R.anim.abc_popup_exit)
                .replace(container, f)
                .addToBackStack(f::class.simpleName)
                .commit()
    }


    private fun clearBackStack() {
        if (fragManager.backStackEntryCount == 0) return
        val first = fragManager.getBackStackEntryAt(0)
        fragManager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    override fun onBackPressed() {
        if (fragManager.backStackEntryCount > 1) fragManager.popBackStack()
        else finish()
    }
}