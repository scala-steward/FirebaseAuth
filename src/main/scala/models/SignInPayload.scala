package models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class SignInPayload(email: String, password: String, returnSecureToken: Boolean = true)

object SignInPayload {
  implicit val decoder: Decoder[SignInPayload] = deriveDecoder
  implicit val encoder: Encoder[SignInPayload] = deriveEncoder
}
