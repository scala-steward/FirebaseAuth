package models

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

case class WithEmailAndPassword(email: String, password: String, returnSecureToken: Boolean = true)

object WithEmailAndPassword {
  implicit val decoder: Decoder[WithEmailAndPassword] = deriveDecoder
  implicit val encoder: Encoder[WithEmailAndPassword] = deriveEncoder
}
