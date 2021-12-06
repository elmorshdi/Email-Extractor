package com.elmorshdi.extractor.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.adapter.EmailsAdapter
import com.elmorshdi.extractor.adapter.PhonesAdapter
import com.elmorshdi.extractor.databinding.FragmentSendBinding
import com.elmorshdi.extractor.other.Utility.getAllEmail
import com.elmorshdi.extractor.other.Utility.getAllPhones
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class SendFragment : Fragment(),EasyPermissions.PermissionCallbacks {
   @Inject
   lateinit var sharedPreferences: SharedPreferences
    private lateinit var sub: String
    private lateinit var msg: String
     lateinit var binding: FragmentSendBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val intent:Intent=requireActivity().intent

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

        binding.button.setOnClickListener(View.OnClickListener {
            when {
                binding.editText.text.isEmpty() -> {
                    val snack = Snackbar.make(view, "Enter the Text first", Snackbar.LENGTH_LONG)
                    snack.show()

                }
                !binding.toggle.isChecked -> {
                    val allList = getAllEmail(binding.editText.text.toString())
                    val emailsAdapter= EmailsAdapter(allList,sub,msg,this.requireContext())
                    Log.d("msg:", " not checked ${allList.size} ")
                    binding.rv.adapter=emailsAdapter
                }
                binding.toggle.isChecked -> {
                    val allList = getAllPhones(binding.editText.text.toString())
                    val phonesAdapter= PhonesAdapter(allList, this.requireContext(),this.requireActivity())
                    Log.d("msg:", "  checked ${allList.size} ")

                    binding.rv.adapter = phonesAdapter

                }
            }

        })
    }
    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            Log.d("tag",it)
            binding.editText.setText(it.toString())
            // Update UI to reflect text being shared
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSendBinding.inflate(inflater, container, false)

        return binding.root    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.chat_list_menu, menu)
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





