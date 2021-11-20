package com.elmorshdi.hapd

import android.content.Intent
import android.content.Intent.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.net.Uri
import android.os.Parcelable


class SendFragment : Fragment(),EasyPermissions.PermissionCallbacks {
    private lateinit var mListView: RecyclerView
    private lateinit var button: Button
    private lateinit var bu1:  ToggleButton
    private lateinit var bu2:  ToggleButton
    private lateinit var sub: String
    private lateinit var msg: String
    lateinit var edtext:EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListView = view.findViewById(R.id.rv)!!
        button = view.findViewById(R.id.button)
        bu1 = view.findViewById(R.id.toggle_bu1)
        bu2 = view.findViewById(R.id.toggle_bu2)
        edtext = view.findViewById<EditText>(R.id.editText)
        val intent:Intent=requireActivity().intent
        bu1.setOnClickListener(View.OnClickListener {
            bu2.isChecked=!bu1.isChecked
        })
        bu2.setOnClickListener(View.OnClickListener {
            bu1.isChecked=!bu2.isChecked
        })
        when {
            intent.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
            else -> {
                // Handle other intents, such as being started from the home screen
            }
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())


        sub = if (sharedPreferences.getBoolean(getString(R.string.key_auto_add_subject), false)) {
            sharedPreferences.getString(getString(R.string.key_select_subject), "").toString()
        } else {
            ""
        }
        msg = if (sharedPreferences.getBoolean(getString(R.string.key_auto_add_msg), false)) {
            sharedPreferences.getString(getString(R.string.key_select_msg), "").toString()
        } else {
            ""
        }

        button.setOnClickListener(View.OnClickListener {
            if (edtext.text.isEmpty()) {
                val snack = Snackbar.make(view, "Enter the Text first", Snackbar.LENGTH_LONG)
                snack.show()

            } else {
                if (bu2.isChecked) {
                    val allList = getAllEmail(edtext.text.toString())
                   val emailsAdapter=EmailsAdapter(allList,sub,msg,this.requireContext())
                    Log.d("msg:", " not checked ${allList.size} ")
                    mListView.adapter = emailsAdapter
                }else if (bu2.isChecked){
                    val allList = getAllPhones(edtext.text.toString())
                    val emailsAdapter=PhonesAdapter(allList, this.requireContext(),this.requireActivity())
                    Log.d("msg:", "  checked ${allList.size} ")
                    mListView.apply {
                        layoutManager = LinearLayoutManager(activity)
                    } // set the custom adapter to the RecyclerView
                    mListView.setAdapter(emailsAdapter);

                }
            }
        })
    }
    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Log.d("tag",it)
            edtext.setText(it.toString())
            // Update UI to reflect text being shared
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.chat_list_menu, menu)
    }


    private fun getAllEmail(s: String): ArrayList<String> {
        var all=ArrayList<String>()
        val sp = " "
        val EMAILREGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        val list = s.split(sp)
        for (it in list) {
            val emailMatcher: Matcher = Pattern.compile(EMAILREGEX).matcher(it)
            var email = ""
            if (emailMatcher.find()) {
                email = emailMatcher.group(0)!!
                all.add(email)}
        }
        return all
    }

    private fun getAllPhones(s: String): ArrayList<String> {
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


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
          }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}





