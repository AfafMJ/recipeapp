package com.afaf.recipeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val edTitle = findViewById<EditText>(R.id.etTitle)
        val edAuthor = findViewById<EditText>(R.id.etAuthor)
        val edIngredents = findViewById<EditText>(R.id.etIngredents)
        val edInstruction = findViewById<EditText>(R.id.etInstruction)
        val buttonCancel = findViewById<Button>(R.id.btCancel)
        val buttonSubmit = findViewById<Button>(R.id.btSubmit)

        val listItem = MainActivity().receipesList!!




        buttonCancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonSubmit.setOnClickListener {
            if (edTitle.text.isNotEmpty()
                && edAuthor.text.isNotEmpty()
                && edIngredents.text.isNotEmpty()
                && edInstruction.text.isNotEmpty() ){
                try {
                    val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://dojo-recipes.herokuapp.com/")
                        .build().create(ApiInterface::class.java)

                    val retrofitData = retrofitBuilder.postData(DataItem(edAuthor.text.toString(), edIngredents.text.toString(), edInstruction.text.toString(), edTitle.text.toString()))

                    if(retrofitData != null){
                        retrofitData.enqueue(object : Callback<Data?> {
                            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                                listItem.add(DataItem(edAuthor.text.toString(), edIngredents.text.toString(), edInstruction.text.toString(), edTitle.text.toString()))
                                Toast.makeText(this@AddRecipe, "Added successfully", Toast.LENGTH_LONG).show()
                            }

                            override fun onFailure(call: Call<Data?>, t: Throwable) {
                                Toast.makeText(this@AddRecipe, "Error: $t", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                    MainActivity().updateData(listItem)


                }catch (e: Exception){


                }



            }
            startActivity(Intent(this, MainActivity::class.java))



        }


    }
}