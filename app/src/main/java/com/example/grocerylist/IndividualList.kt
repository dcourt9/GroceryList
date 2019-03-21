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


class IndividualList : Fragment() {

    //private lateinit var v: View

    // Button that opens dialog
    private var addButton: Button? = null

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
        val model = activity?.let { ViewModelProviders.of(it).get(MyViewModel::class.java)}

        //addButton = v.findViewById(R.id.add_button) as Button

        //Clicking open dialog
        (v.findViewById(R.id.add_button) as Button).setOnClickListener {
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

            //on clicking save item button
            saveItem?.setOnClickListener {
                // Gets item data from user dialog
                val name = nameEdit?.getText().toString()
                val price = priceEdit?.getText().toString()
                val quantity = quantityEdit?.getText().toString()
                val dept = departmentEdit?.getText().toString()

                alertDialog.cancel()
            }
            closeItem?.setOnClickListener(View.OnClickListener { alertDialog.cancel() })
        }

        return inflater.inflate(R.layout.fragment_individual_list, container, false)
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
}
