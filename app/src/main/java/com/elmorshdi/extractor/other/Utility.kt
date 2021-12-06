package com.elmorshdi.extractor.other

import android.Manifest
import android.content.Context
import android.util.Log
import pub.devrel.easypermissions.EasyPermissions
import java.util.regex.Matcher
import java.util.regex.Pattern

object  Utility {

    fun hasPermissions(context: Context) =EasyPermissions.hasPermissions(
        context,
        Manifest.permission.CALL_PHONE
    )
    fun getAllPhones(s: String): ArrayList<String> {
        var all=ArrayList<String>()
        val sp = " "
        val PHONEREGEX ="(01)[0-9]{9}"
        val list = s.split(sp)
        for (it in list) {
            val phoneMatcher: Matcher = Pattern.compile(PHONEREGEX).matcher(it)
            var phone = ""
            if (phoneMatcher.find())  {
                phone = phoneMatcher.group(0)!!
                all.add(phone)
            }
        }

        return all
    }
     fun getAllEmail(s: String): ArrayList<String> {
        var all=ArrayList<String>()
        val sp = " "
        val EMAILREGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        val list = s.split(sp)
        for (it in list) {
            val s=it.trim().lowercase()
            Log.d("msg:", " not lower ${s} ")

            val emailMatcher: Matcher = Pattern.compile(EMAILREGEX).matcher(s)
            var email = ""
            if (emailMatcher.find()) {
                email = emailMatcher.group(0)!!
                all.add(email)}
        }
        return all
    }
}