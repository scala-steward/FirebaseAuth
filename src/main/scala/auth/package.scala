import zio.Has

package object auth {
  type FirebaseAuth = Has[FirebaseAuth.Service]
}
