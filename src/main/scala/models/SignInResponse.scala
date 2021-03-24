package models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class SignInResponse(idToken: String,
                          email: String,
                          refreshToken: String,
                          expiresIn: Long,
                          localId: String)

object SignInResponse {
  implicit val decoder: Decoder[SignInResponse] = deriveDecoder
  implicit val encoder: Encoder[SignInResponse] = deriveEncoder
}
