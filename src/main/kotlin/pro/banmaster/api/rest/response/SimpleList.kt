package pro.banmaster.api.rest.response

import org.json.JSONObject
import pro.banmaster.api.struct.preprocessResponse
import pro.banmaster.api.rest.JSONAPI2

class SimpleList<E>: ArrayList<E>() {
    companion object {
        fun <E> parse(response: JSONAPI2.Response<JSONObject>, converter: (Any) -> E): SimpleList<E> {
            preprocessResponse(response)
            val list = SimpleList<E>()
            response.response.getJSONArray("DATA").forEach { list.add(converter.invoke(it)) }
            return list
        }
    }
}
