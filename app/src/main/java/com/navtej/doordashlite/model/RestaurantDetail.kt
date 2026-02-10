package com.navtej.doordashlite.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RestaurantDetail(
    @SerializedName("id") @Expose val id: String? = null,
    @SerializedName("name") @Expose val name: String? = null,
    @SerializedName("description") @Expose val description: String? = null,
    @SerializedName("cover_img_url") @Expose val cover_img_url: String? = null,
    @SerializedName("status") @Expose val status: String? = null,
    @SerializedName("header_img_url") @Expose val headerImgURL: String? = null,
    @SerializedName("delivery_fee") @Expose val deliveryFee: String? = null
) {
    val coverURL: String?
        get() = cover_img_url

    val shortDescription: String?
        get() {
            val d = description ?: return null
            return if (d.length > MAX_DESCRIPTION_ALLOWED) {
                d.substring(0, MAX_DESCRIPTION_ALLOWED) + "..."
            } else {
                d
            }
        }

    override fun toString(): String =
        listOf(id, name, deliveryFee, status, cover_img_url).joinToString(" ")

    companion object {
        private const val MAX_DESCRIPTION_ALLOWED = 100
    }
}
