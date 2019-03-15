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

    fun flattenDictionary(dict: Map<String, Any>) : Any {
        val array = mutableListOf<Any>()

        for (i in 0 until dict.keys.size) {
            array[i] = "***" // just to make sure the array is not empty
        }

        if (dict.keys.first().matches(Regex(".*\\d+.*"))) {
            for (key in dict.keys) {
                if (dict[key] is Map<*, *>) {
                    array[key.toInt()] = flattenDictionary(dict[key] as Map<String, Any>)
                } else {
                    array[key.toInt()] = dict[key] as Map<String, Any>
                }
            }

            array.remove("***")
            return array
        } else {
            val dictionary = mutableMapOf<String, Any>()

            for (key in dict.keys) {
                if (dict[key] is Map<*, *>) {
                    dictionary[key] = flattenDictionary(dict[key] as Map<String, Any>)
                } else {
                    dictionary[key] = dict[key] as Map<String, Any>
                }
            }

            return dictionary
        }
    }

    fun articleWithNumericAddress(numericAddress: Int) : Map<String, Any>?{
        val array = articles["Articles"] as List<Any>

        if (numericAddress < array.size) {  // -1 due to 0 indexing
            return array[numericAddress] as Map<String, Any>
        }

        return null
    }
}