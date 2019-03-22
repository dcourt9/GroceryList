package com.example.grocerylist

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import android.R.attr.button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_individual_list.*
import kotlinx.android.synthetic.main.fragment_store_lists.*


class IndividualList : Fragment() {

    //private lateinit var v: View

    // Button that opens dialog
    //private var addButton: Button? = null

    //text field for item name
    private var nameEdit: EditText? = null
    //text field for item price
    private var priceEdit: EditText? = null
    //text field for item quant
    private var quantityEdit: EditText? = null
    //text field for item dept
    private var departmentEdit: EditText? = null

    //dialog view
    private var popupView: View? = null
    //button that adds new item
    private var saveItem: Button? = null
    //button that closes dialog
    private var closeItem: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_individual_list, container, false)
        val model = activity?.run {
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        }

        val recyclerView = v.findViewById<RecyclerView>(R.id.food_list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val addButton = v.findViewById<Button>(R.id.add_button)

        //Clicking open dialog
        addButton?.setOnClickListener {

            //Creates alertDialogBuilder
            val alertDialogBuilder = AlertDialog.Builder(this.context)
            // Sets title icon can not cancel properties.
            alertDialogBuilder.setTitle("Item Data Dialog")
            alertDialogBuilder.setIcon(R.drawable.ic_launcher_background)
            alertDialogBuilder.setCancelable(false)

            // Initialize popup dialog view
            initPopupViewControls()

            // Set the inflated layout view object to the AlertDialog builder
            alertDialogBuilder.setView(popupView)

            //Creates and shows alert dialog
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

            val adapter = FoodListAdapter(model!!)
            recyclerView.adapter = adapter
            model.allFoods.observe(
                this,
                Observer<List<FoodItem>>{food   ->
                    food?.let{
                       adapter.setItem(it)
                    }
                }
            )


            //on clicking save item button
            saveItem?.setOnClickListener {
                // Gets item data from user dialog
                val name = nameEdit?.getText().toString()
                val price = priceEdit?.getText().toString().toDouble()
                val quantity = quantityEdit?.getText().toString().toDouble()
                val dept = departmentEdit?.getText().toString()
                model?.makeFood(name, price, quantity, dept)
                alertDialog.cancel()
            }
            closeItem?.setOnClickListener(View.OnClickListener { alertDialog.cancel() })

        }

        return v
    }


    /* Initialize popup dialog view and buttons */
    private fun initPopupViewControls() {
        // Get layout inflater object.
        val layoutInflater = LayoutInflater.from(this.context)

        //Inflates popup dialog from its xml
        popupView = layoutInflater.inflate(R.layout.popup_input_dialog, null)

        // Gets the ui controls in dialog box
        nameEdit = popupView?.findViewById(R.id.name) as EditText
        priceEdit = popupView?.findViewById(R.id.price) as EditText
        quantityEdit = popupView?.findViewById(R.id.quantity) as EditText
        departmentEdit = popupView?.findViewById(R.id.department) as EditText
        saveItem = popupView?.findViewById(R.id.addItem)
        closeItem = popupView?.findViewById(R.id.closeItem)
    }


    inner class FoodListAdapter(model : MyViewModel):
        RecyclerView.Adapter<FoodListAdapter.ItemViewHolder>(){
        private var items = emptyList<FoodItem>()

        internal fun setItem(movies: List<FoodItem>) {
            this.items = movies
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {

            return items.size
        }

        private var VM = model

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {


            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
            return ItemViewHolder(v)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


            //holder.bindItems(movieList[position])

            //Glide.with(this@ListScreen).load(resources.getString(R.string.picture_base_url)+movies[position].poster_path).apply( RequestOptions().override(128, 128)).into(holder.view.findViewById(R.id.poster))

            holder.view.findViewById<TextView>(R.id.item).text=items[position].item_name

            holder.view.findViewById<TextView>(R.id.departmentT).text=items[position].department

            holder.view.findViewById<TextView>(R.id.list_price).text=items[position].price.toString()

            //holder.view.findViewById<TextView>(R.id.quantity).text=items[position].quantity.toString()

            //holder.itemView.setOnClickListener(){

                //VM?.setFood(food[position])
              //  holder.onClick(view)
                // interact with the item


            //}

        }


        inner class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
            override fun onClick(view: View?){

              //  if (view != null) {
                //    Navigation.findNavController(view).navigate(R.id.action_listScreen_to_detailScreen)
                    //this.
                //}


            }

        }
    }

}
