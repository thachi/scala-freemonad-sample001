package interpreter

import cats.{Id, ~>}
import core._
import models.{Blog, Image}

import scala.collection.mutable

object DebugRepositoryInterpreter {

  private val images = mutable.HashMap.empty[Identifier[Image], Image]
  private val blogs = mutable.HashMap.empty[Identifier[Blog], Blog]

  def getImages: Seq[Image] = images.values.toSeq
  def getBlogs: Seq[Blog] = blogs.values.toSeq


  def impureCompiler: RepositoryOperation ~> Id =
    new (RepositoryOperation ~> Id) {
      def apply[A](fa: RepositoryOperation[A]): Id[A] =
        fa match {
          case CreateEntity(entity) =>

            entity match {
              case i: Image => images += i.id -> i
              case b: Blog => blogs += b.id -> b
            }
            ().asInstanceOf[A]

          case d@DeleteEntity(id) =>
            d.classTag.runtimeClass.getSimpleName match {
              case "Image" => images -= id.asInstanceOf[Identifier[Image]]
              case "Blog" => blogs -= id.asInstanceOf[Identifier[Blog]]
            }
            ().asInstanceOf[A]

          case a: AllocateId[_] =>

            val id = a.classTag.runtimeClass.getSimpleName match {
              case "Image" => StringIdentifier("image-" + (images.size + 1))
              case "Blog" => StringIdentifier("blog-" + (blogs.size + 1))
            }
            println(s"allocate: $id")
            id.asInstanceOf[A]
        }
    }
}



