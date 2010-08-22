import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
	// From http://github.com/jboner/akka/blob/master/project/build/AkkaProject.scala
  lazy val AkkaRepo               = MavenRepository("Akka Repository", "http://scalablesolutions.se/akka/repository")
  lazy val CodehausRepo           = MavenRepository("Codehaus Repo", "http://repository.codehaus.org")
  lazy val FusesourceSnapshotRepo = MavenRepository("Fusesource Snapshots", "http://repo.fusesource.com/nexus/content/repositories/snapshots")
  lazy val GuiceyFruitRepo        = MavenRepository("GuiceyFruit Repo", "http://guiceyfruit.googlecode.com/svn/repo/releases/")
  lazy val JBossRepo              = MavenRepository("JBoss Repo", "https://repository.jboss.org/nexus/content/groups/public/")
  lazy val JavaNetRepo            = MavenRepository("java.net Repo", "http://download.java.net/maven/2")
  lazy val SonatypeSnapshotRepo   = MavenRepository("Sonatype OSS Repo", "http://oss.sonatype.org/content/repositories/releases")
  lazy val SunJDMKRepo            = MavenRepository("Sun JDMK Repo", "http://wp5.e-taxonomy.eu/cdmlib/mavenrepo")

  override def libraryDependencies = Set(
    "se.scalablesolutions.akka" % "akka-core_2.8.0" % "0.10"
  ) ++ super.libraryDependencies
}
