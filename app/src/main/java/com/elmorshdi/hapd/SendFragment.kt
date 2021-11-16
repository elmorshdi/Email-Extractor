package com.elmorshdi.hapd

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar


class SendFragment : Fragment() {
    private var emailList = ArrayList<String>()
    private lateinit var arrayAdapter: ArrayAdapter<*>
    private lateinit var mListView: ListView
    private lateinit var button: Button
    private lateinit var txt: TextView
    private lateinit var sub: String
    private lateinit var msg: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mListView = view.findViewById<ListView>(R.id.rv)!!
        button = view.findViewById(R.id.button)
        txt = view.findViewById(R.id.textView)
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
                emailList = getEmails(edtext.text.toString())
                arrayAdapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    emailList
                )
                mListView.adapter = arrayAdapter
            }
        })
        mListView.setOnItemClickListener { parent, _, position, id ->
            val email: String =
                arrayAdapter.getItem(position) as String // The item that was clicked
            sendEmail(email)
            Log.d("msg:", email)
        }
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

    private fun sendEmail(email: String?) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(email.toString()))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub)
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg)

        emailIntent.type = "message/rfc822"

        startActivity(emailIntent)

    }

    private fun getEmails(s: String): ArrayList<String> {
        val sp = " "
        val ch: Array<Char> =
            arrayOf('(', '"', ':', '\'', '{', '[', ')', '}', ']', '<', '>', ',', '.')
        val list = s.split(sp)
        val pattern = "@".toRegex()
        val emails = ArrayList<String>()
        for (it in list) {
            val chars = it.toCharArray()

            if (pattern.containsMatchIn(it)) {
                String(chars)
                when {
                    ch.contains(it[0]) -> {
                        Log.d("msg:", chars[0] + "  ")
                        chars[0] = ' '
                        Log.d("msg:", "$it  ")
                    }
                }
                when {
                    ch.contains(chars[chars.size - 1]) -> {
                        Log.d("msg:", chars[chars.size - 1] + " last ")
                        chars[chars.size - 1] = ' '
                    }
                }

                emails.add(String(chars))


            }
        }
        return emails
    }


}





