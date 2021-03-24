package models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class GetUserDataResponse(users: List[UserData])

object GetUserDataResponse {
  implicit val decoder: Decoder[GetUserDataResponse] = deriveDecoder
  implicit val encoder: Encoder[GetUserDataResponse] = deriveEncoder
}
