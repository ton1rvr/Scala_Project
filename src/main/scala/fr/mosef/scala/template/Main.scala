package fr.mosef.scala.template

import fr.mosef.scala.template.job.Job
import fr.mosef.scala.template.processor.Processor
import fr.mosef.scala.template.processor.impl.ProcessorImpl
import fr.mosef.scala.template.reader.Reader
import fr.mosef.scala.template.reader.impl.ReaderImpl
import org.apache.spark.sql.SparkSession
import fr.mosef.scala.template.writer.Writer
import org.apache.spark.SparkConf
import com.globalmentor.apache.hadoop.fs.BareLocalFileSystem
import org.apache.hadoop.fs.FileSystem

import scala.io.Source

object Main extends App {

    val sparkSession = SparkSession
      .builder()
      .appName("BaseCarboneAPI")
      .master("local[*]")
      .enableHiveSupport()
      .getOrCreate()

    // ⚠️ API publique : pas besoin de clé ici
    val apiUrl = "https://api.gouv.fr/documentation/api-rappel-conso"

    try {
      val response = Source.fromURL(apiUrl).mkString
      println("Réponse brute de l'API Base Carbone :")
      println(response)

//      val json = JSON.parseFull(response)
//      #println("JSON parsé (simplifié) :")
//      #println(json)

      // TODO : pour parser dans un DataFrame, il faudrait utiliser un vrai parser JSON (comme `spark.read.json`)
      // Exemple (si tu veux aller plus loin) :
      import sparkSession.implicits._
      val rdd = sparkSession.sparkContext.parallelize(Seq(response))
      val df = sparkSession.read.json(rdd)
      df.show(false)

    } catch {
      case e: Exception =>
        println(s"Erreur lors de l'appel à l'API Base Carbone : ${e.getMessage}")
    }

    sparkSession.stop()
  }
