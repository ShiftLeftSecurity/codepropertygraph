package io.shiftleft.semanticcpg.language.ext.semanticcpg

/** Sole purpose of this trait is to allow to enrich ('pimp') this DSL without the need of additional
  * imports a la `import x.y.z.Implicits._`. We're making use of the fact that package objects of
  * inherited traits are automatically part of implicit scope. This way, these extensions also show
  * up in scaladoc.
  *
  * Important: this project should *not* define anything in the `ext` package object. If it does,
  * it will be shadowed by enriching libraries.
  */
trait Enrichable
