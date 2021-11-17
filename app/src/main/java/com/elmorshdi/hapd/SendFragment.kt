package com.elmorshdi.hapd

import android.content.Intent
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
import java.util.regex.Matcher
import java.util.regex.Pattern


class SendFragment : Fragment() {
    private var emailList = ArrayList<String>()
    private lateinit var arrayAdapter: ArrayAdapter<*>
    private lateinit var mListView: RecyclerView
    private lateinit var button: Button
    private lateinit var bu: ToggleButton
    private lateinit var sub: String
    private lateinit var msg: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListView = view.findViewById(R.id.rv)!!
        button = view.findViewById(R.id.button)
        bu = view.findViewById(R.id.toggle_bu)
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
            val edtext = view.findViewById<EditText>(R.id.editText)
            if (edtext.text.isEmpty()) {
                val snack = Snackbar.make(view, "Enter the Text first", Snackbar.LENGTH_LONG)
                snack.show()

            } else {
                if (!bu.isChecked) {
                    val allList = getAllEmail(edtext.text.toString())
                   val emailsAdapter=EmailsAdapter(allList,sub,msg,this.requireContext())
                    Log.d("msg:", " not checked ${allList.size} ")
                    mListView.adapter = emailsAdapter
                }else{
                    val allList = getAllPhones(edtext.text.toString())

                    val emailsAdapter=PhonesAdapter(allList, this.requireContext())
                    Log.d("msg:", "  checked ${allList.size} ")
                    mListView.apply {
                        layoutManager = LinearLayoutManager(activity)
                    } // set the custom adapter to the RecyclerView
                    mListView.setAdapter(emailsAdapter);

                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.chat_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                val navHostFragment =
                    activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
                val navController = navHostFragment.navController
                val action = SendFragmentDirections.actionSendFragmentToSettingsFragment()
                navController.navigate(action)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getAllPhones(s: String): ArrayList<String> {
        var all=ArrayList<String>()
        val sp = " "
        val list = s.split(sp)
        for (it in list) {

            getPhone(it)?.let { it1 -> all.add(it1) }
        }

        return all
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
    private fun getEmail(Document: String): String? {
        val EMAILREGEX =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"

        val emailMatcher: Matcher = Pattern.compile(EMAILREGEX).matcher(Document)
        var email = ""
        if (emailMatcher.find()) email = emailMatcher.group(0)

        if ( email != "" ) {
            return email
       }
        return null
    }
    private fun getPhone(Document: String): String? {
         val PHONEREGEX ="(01)[0-9]{9}"
//            "(?:(?:\\+?([1-9]|[0-9][0-9]|[0-9][0-9][0-9])\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([0-9][1-9]|[0-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?"
        //      Using regex to find emails and phone numbers===========
        val phoneMatcher: Matcher = Pattern.compile(PHONEREGEX).matcher(Document)
        var phone = ""
        if (phoneMatcher.find()) phone = phoneMatcher.group(0)

        if (phone != "" ) {
            return  phone
        }
        return null
    }


}





