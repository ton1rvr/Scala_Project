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

    sparkSession.stop()
  }
