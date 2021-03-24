package models

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

case class UserData(
                     localId: String,
                     email: String,
                     emailVerified: Boolean,
                     displayName: Option[String],
                     providerUserInfo: List[ProviderUserInfo],
                     photoUrl: Option[String],
                     passwordHash: String,
                     passwordUpdatedAt: Double,
                     validSince: String,
                     disabled: Boolean,
                     lastLoginAt: String,
                     createdAt: String,
                     customAuth: Option[Boolean]
                   )

object UserData {
  implicit val decoder: Decoder[UserData] = deriveDecoder
  implicit val encoder: Encoder[UserData] = deriveEncoder
}
