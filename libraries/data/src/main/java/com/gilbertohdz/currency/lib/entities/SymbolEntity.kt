package com.gilbertohdz.currency.lib.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "symbols")
data class SymbolEntity(
  @PrimaryKey
  val currency: String,
  val description: String
): Parcelable