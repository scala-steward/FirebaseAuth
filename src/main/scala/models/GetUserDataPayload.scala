package models

import io.circe.{Codec, Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class GetUserDataPayload(idToken: String)

object GetUserDataPayload {
  implicit val decoder: Decoder[GetUserDataPayload] = deriveDecoder
  implicit val encoder: Encoder[GetUserDataPayload] = deriveEncoder
  implicit val codec: Codec[GetUserDataPayload] = Codec.from(decoder, encoder)
}