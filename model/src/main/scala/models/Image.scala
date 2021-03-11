package models

import core.{Entity, Identifier, Repository, Undefined}


case class ImageId(id: String) extends Identifier[Image]

case class Image(id: Identifier[Image] = Undefined, url: String, alt: Option[String] = None) extends Entity[Image]

object ImageRepository extends Repository[Image]