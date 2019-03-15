package com.westsamoaconsult.labtests.database

import com.google.firebase.database.*

class FBDatabase {
    private var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Articles")
    lateinit var articles: MutableMap<String, Any>
    var usable =  false

    init {
        retrieveArticles()
    }

    private fun retrieveArticles() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.value?.let {
                    articles = mutableMapOf<String, Any>()
                    articles["Articles"] = it
                    usable = true
                    return
                }
                usable = false
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                usable = false
            }
        })
    }

    fun articleWithNumericAddress(numericAddress: Int) : Map<String, Any>?{
        val array = articles["Articles"] as List<Any>

        if (numericAddress < array.size) {  // -1 due to 0 indexing
            return array[numericAddress] as Map<String, Any>
        }

        return null
    }
}