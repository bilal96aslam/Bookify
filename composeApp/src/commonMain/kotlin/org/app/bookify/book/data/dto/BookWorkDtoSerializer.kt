package org.app.bookify.book.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object BookWorkDtoSerializer : KSerializer<BookWorkDto> {
    // on which fields we are looking for for deserialize
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookWorkDto::class.simpleName!!
    ) {
        element<String?>("description")
    }

    // step by step decode those fields which care about and return these as our desired obj
    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var des: String? = null
        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    val jsonDecoder = decoder as JsonDecoder
                    val element = jsonDecoder.decodeJsonElement() // anything related to json
                    des = if (element is JsonObject) {
                        decoder.json.decodeFromJsonElement(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString) {
                        element.content
                    } else null
                }

                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }
        }
        return@decodeStructure BookWorkDto(des)
    }

    /** if you have BookWorkDto and want to push that with the API, then we
    have take this kotlin obj and serialize in json **/
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure(
        descriptor
    ) {
        value.description?.let {
            encodeStringElement(descriptor, 0, it)
        }
    }

}
