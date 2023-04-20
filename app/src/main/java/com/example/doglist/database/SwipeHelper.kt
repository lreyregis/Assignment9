package com.example.doglist.database

import android.icu.lang.UCharacter.IndicPositionalCategory.LEFT
import android.icu.lang.UCharacter.IndicPositionalCategory.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import javax.security.auth.callback.Callback


internal class SwipeController : Callback {
    fun getMovementFlags(dogRecyclerView: RecyclerView?, dogViewHolder: ViewHolder?): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    fun onMove(dogRecyclerView: RecyclerView?, dogViewHolder: ViewHolder?, target: ViewHolder?): Boolean {
        return false
    }

    fun onSwiped(dogRecyclerView: ViewHolder?, direction: Int) {}
}