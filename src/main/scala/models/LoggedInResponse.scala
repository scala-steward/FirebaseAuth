package models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class LoggedInResponse(idToken: String,
                            email: String,
                            refreshToken: String,
                            expiresIn: Long,
                            localId: String)

object LoggedInResponse {
  implicit val decoder: Decoder[LoggedInResponse] = deriveDecoder
  implicit val encoder: Encoder[LoggedInResponse] = deriveEncoder
}
