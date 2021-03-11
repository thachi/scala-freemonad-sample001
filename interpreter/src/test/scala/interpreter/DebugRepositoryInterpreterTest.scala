package interpreter

import core.StringIdentifier
import org.scalatest.funsuite.AnyFunSuite
import models._

class DebugRepositoryInterpreterTest extends AnyFunSuite {

  test("ざっくり試してみる") {
    val create = Service.createBlog("たいとる", "コンテンツ", "https://image.example.com/path/to/image.png")
    val result = create.foldMap(DebugRepositoryInterpreter.impureCompiler)

    assert(result == StringIdentifier("blog-1"))

    assert(DebugRepositoryInterpreter.getBlogs.size == 1)
    assert(DebugRepositoryInterpreter.getBlogs.head.id == StringIdentifier("blog-1"))
    assert(DebugRepositoryInterpreter.getImages.size == 1)
    assert(DebugRepositoryInterpreter.getImages.head.id == StringIdentifier("image-1"))
  }

}
