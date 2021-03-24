package models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class ProviderUserInfo(providerId: String, federatedId: String)

object ProviderUserInfo {
  implicit val decoder: Decoder[ProviderUserInfo] = deriveDecoder
  implicit val encoder: Encoder[ProviderUserInfo] = deriveEncoder
}
