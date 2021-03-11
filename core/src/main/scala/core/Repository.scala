package core

import scala.reflect.ClassTag


trait RepositoryOperation[A]

case class AllocateId[A <: Entity[A]]()(implicit val classTag: ClassTag[A]) extends RepositoryOperation[Identifier[A]]

case class CreateEntity[A <: Entity[A]](entity: A) extends RepositoryOperation[Unit]

case class DeleteEntity[A <: Entity[A]](id: Identifier[A])(implicit val classTag: ClassTag[A]) extends RepositoryOperation[Unit]

import cats.free.Free

trait Repository[A <: Entity[A]] {
  type KVEntityOperation[E] = Free[RepositoryOperation, E]

  import cats.free.Free.liftF

  def allocate()(implicit classTag: ClassTag[A]): KVEntityOperation[Identifier[A]] = liftF[RepositoryOperation, Identifier[A]](AllocateId[A]())

  def create(entity: A): KVEntityOperation[Unit] = liftF[RepositoryOperation, Unit](CreateEntity[A](entity))

}


trait Entity[+E]

trait Identifier[+E <: Entity[E]]

case class StringIdentifier[E <: Entity[E]](value: String) extends Identifier[E]

case object Undefined extends Identifier[Nothing]

