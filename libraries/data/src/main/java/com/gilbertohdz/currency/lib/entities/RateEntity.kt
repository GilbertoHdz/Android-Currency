package com.gilbertohdz.currency.lib.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rates")
data class RateEntity(
  @PrimaryKey
  val currency: String,
  val value: Double
)