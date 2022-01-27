package net.jahez.jahezchallenge.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.jahez.jahezchallenge.BaseAppCompatActivity
import net.jahez.jahezchallenge.R
import net.jahez.jahezchallenge.databinding.ActivityMainBinding
import net.jahez.jahezchallenge.util.CacheHelperLang
import net.jahez.jahezchallenge.util.Constant
import net.jahez.jahezchallenge.util.Resource
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseAppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        restaurantAdapter = RestaurantAdapter()

        binding.recyclerView.apply {
            adapter = restaurantAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.imageViewFilter.setOnClickListener {
            showFilterDialog()
        }

        observeData()
    }

    private fun observeData() {
        viewModel.restaurants.observe(this) { result ->
            restaurantAdapter.submitList(result.data)
            when (result) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.d("Resource.Error", result.error!!.printStackTrace().toString())
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.d("Resource.Success", result.data.toString())

                }
            }
        }
    }

    private fun showFilterDialog() {

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle(getString(R.string.title))
        val items =
            arrayOf(
                getString(R.string.all),
                getString(R.string.offers),
                getString(R.string.distance)
            )
        mBuilder.setItems(
            items
        ) { dialog, which ->
            when (which) {
                0 -> {
                    viewModel.restaurants.observe(this) { result ->
                        restaurantAdapter.submitList(result.data!!)

                    }

                    dialog.dismiss()

                }
                1 -> {
                    viewModel.restaurants.observe(this) { result ->
                        restaurantAdapter.submitList(result.data!!.filter { it.hasOffer!! })

                    }


                    dialog.dismiss()

                }
                else -> {
                    viewModel.restaurants.observe(this) { result ->
                        restaurantAdapter.submitList(result.data!!.sortedByDescending { it.distance!! })

                    }
                    dialog.dismiss()
                }


            }

        }

        val mDialog = mBuilder.create()
        mDialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.lang_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_ar -> {
                CacheHelperLang(applicationContext).saveCurrentLanguage(Constant.LANGUAGE_AR)
                dLocale = Locale("ar")
                recreate()
                true
            }
            R.id.action_en -> {

                CacheHelperLang(applicationContext).saveCurrentLanguage(Constant.LANGUAGE_AR)
                dLocale = Locale("en")
                recreate()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}