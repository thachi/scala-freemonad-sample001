package models

object Service {

  def createBlog(title: String, content: String, imageurl: String) = for {
    imageId <- ImageRepository.allocate()
    _ <- ImageRepository.create(Image(imageId, imageurl))
    blocId <- BlogRepository.allocate()
    blog = Blog(blocId, title, content, imageId)
    _ <- BlogRepository.create(blog)
  } yield blocId

}
