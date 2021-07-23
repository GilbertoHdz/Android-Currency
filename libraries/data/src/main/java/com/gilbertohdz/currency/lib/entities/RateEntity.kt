package com.gilbertohdz.currency.lib.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rates")
data class RateEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  val baseId: String,
  val currency: String,
  val value: Double
): Parcelable