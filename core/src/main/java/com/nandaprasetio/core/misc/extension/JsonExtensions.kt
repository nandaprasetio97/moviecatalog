package com.nandaprasetio.core.misc.extension

import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult

@Suppress("unused")
fun<T> JsonDeserializer<BaseHttpResult<ListLoaderOutput<T>>>.getNextPage(jsonObject: JsonObject): Int? {
    return jsonObject.let {
        it.get("page")?.asInt?.let { it2 ->
            it.get("total_pages")?.asInt?.let { it3 ->
                if (it2 < it3) { it2 + 1 } else { null }
            }
        }
    }
}

val JsonElement.asStringOrNull: String?
    get() {
        return if (this.isJsonNull) { null } else {
            if (this.asString.isNullOrBlank()) { null } else { this.asString }
        }
    }