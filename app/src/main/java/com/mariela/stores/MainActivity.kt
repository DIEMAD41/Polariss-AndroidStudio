package com.mariela.stores

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import com.mariela.stores.databinding.ActivityMainBinding
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity(), OnClickListener, MainAux {
    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        /*mBinding.btnSave.setOnClickListener {
            val storeEntity = StoreEntity(name=mBinding.etName.text.toString().trim())

            Thread{
                StoreApplication.database.storeDao().addStore(storeEntity)
            }.start()

            mAdapter.add(storeEntity)
        }*/

        mBinding.fab.setOnClickListener{
            launchEditFragment()

        }
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        mAdapter = StoreAdapter(mutableListOf(),this)
        mGridLayout = GridLayoutManager(this,2)
        getAllSatores()
        mBinding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter=mAdapter
        }
    }

    private fun getAllSatores(){
        val queue = LinkedBlockingQueue<MutableList<StoreEntity>>()
        Thread{
            val stores = StoreApplication.database.storeDao().getAllStores()
            queue.add(stores)
        }.start()

        mAdapter.setStores(queue.take())
    }

    override fun onClick(storeEntity:StoreEntity){

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFacorite=!storeEntity.isFacorite
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().updateStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.update(queue.take())
    }
    override fun onDeleteStore(storeEntity:StoreEntity){
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        mAdapter.delete(queue.take())
    }

    private fun launchEditFragment(){
        val fragment = EditStoreFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransation = fragmentManager.beginTransaction()
        fragmentTransation.addToBackStack(null)
        fragmentTransation.add(R.id.containerMain,fragment)
        fragmentTransation.commit()
        hideFab()
    }
    /*
    *Interface MainAux
     */
    override fun hideFab(isVosible: Boolean) {
        if(isVosible) mBinding.fab.show() else mBinding.fab.hide()
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mBinding.containerMain.windowToken,0)

    }

    override fun addStore(storeEntity: StoreEntity) {
        mAdapter.add(storeEntity)
    }
}