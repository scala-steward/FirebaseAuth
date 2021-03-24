import zio.Has

package object client {
  type HttpClient = Has[HttpClient.Service]
}
