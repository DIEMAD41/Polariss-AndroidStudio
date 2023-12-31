package com.mariela.stores

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mariela.stores.databinding.FragmentEditStoreBinding
import java.util.concurrent.LinkedBlockingQueue

class EditStoreFragment : Fragment() {
    private lateinit var mBinding: FragmentEditStoreBinding
    private var mActivity: MainActivity?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentEditStoreBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title = getString(R.string.edit_store_title_add)
        setHasOptionsMenu(true)
        mBinding.etPhotoUrl.addTextChangedListener {
            Glide.with(this)
                .load(mBinding.etPhotoUrl.text.toString())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mBinding.imgPhoto )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressedDispatcher?.onBackPressed()
                true
            }
            R.id.action_save ->{
                val id:Long = -1
                val store = StoreEntity(
                    name = mBinding.etName.text.toString().trim(),
                    phone = mBinding.etPhone.text.toString().trim(),
                    website = mBinding.etWebsite.text.toString().trim(),
                    photoUrl = mBinding.etPhotoUrl.text.toString().trim()
                )
                val queue = LinkedBlockingQueue<Long?>()
                Thread{
                    val id = StoreApplication.database.storeDao().addStore(store)
                    queue.add(id)
                }.start()
                mActivity?.addStore(store)
                hideKeyboard()
                queue.take()?.let {
                    store.id = id
                    /*Snackbar.make(mBinding.root,getString(R.string.edit_store_message_save_succes),Snackbar.LENGTH_SHORT).show()*/
                    Toast.makeText(mActivity,R.string.edit_store_message_save_succes,Toast.LENGTH_SHORT).show()
                    mActivity?.onBackPressedDispatcher?.onBackPressed()
                }

                true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
        //return super.onOptionsItemSelected(item)
    }

    private fun hideKeyboard(){
        val imm = mActivity?.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken,0)
    }

    override fun onDestroyView() {
        //Se llama al del main para que jale
        mActivity?.hideKeyboard()
        super.onDestroyView()
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)
        setHasOptionsMenu(false)
        super.onDestroy()
    }
}