package com.example.e_book.repo

import android.content.ContentValues.TAG
import android.util.Log
import com.example.e_book.common.BOOK_CATEGORY_PATH
import com.example.e_book.common.BOOK_PATH
import com.example.e_book.common.ResultState
import com.example.e_book.data.BookCategoryModels
import com.example.e_book.data.BookModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose

class repo @Inject constructor(val firebaseDatabase: FirebaseDatabase) {


    fun getAllBooks(): Flow<ResultState<List<BookModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var items: List<BookModels> = emptyList()
                items = dataSnapshot.children.map { value ->
                    value.getValue(BookModels::class.java)!!

                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(daabaseError: DatabaseError) {
                trySend(ResultState.Error(daabaseError.message))


            }
        }

        firebaseDatabase.reference.child(BOOK_PATH)
            .addValueEventListener(postListener)


        awaitClose {
            close()
        }
    }

    fun getAllCategories(): Flow<ResultState<List<BookCategoryModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var items: List<BookCategoryModels> = emptyList()
                items = dataSnapshot.children.map { value ->
                    value.getValue<BookCategoryModels>()!!

                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(daabaseError: DatabaseError) {
                trySend(ResultState.Error(daabaseError.message))


            }
        }

        firebaseDatabase.reference.child(BOOK_CATEGORY_PATH)
            .addValueEventListener(postListener)


        awaitClose {
            close()
        }
    }


}