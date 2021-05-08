package com.moesif.filter

import javax.inject.{Inject, Provider, Singleton}
import play.api.Configuration

/**
  * Configuration for the moesif api filter
  * @param maxApiEventsToHoldInMemory number of API events that can be cached in memory before sending to Moesif
  * @param moesifApplicationId Your Moesif Application Id can be found in the Moesif Portal. After signing up for a Moesif account, your Moesif Application Id
  *                            will be displayed during the onboarding steps. You can always find your Moesif Application Id at any time by logging
  *                            into the Moesif Portal, click on the top right menu, and then clicking Installation.
  */
case class MoesifApiFilterConfig(maxApiEventsToHoldInMemory: Int, moesifApplicationId: String, requestBodyProcessingEnabled: Boolean = true, moesifCollectorEndpoint: String)
object MoesifApiFilterConfig {
  def fromConfiguration(conf: Configuration): MoesifApiFilterConfig = {
    val config = conf.get[Configuration]("play.filters.moesifApiFilter")
    val maxApiEventsToHoldInMemory = config.getOptional[Int]("maxApiEventsToHoldInMemory").getOrElse(10)
    val reqBodyProcessing = config.getOptional[Boolean]("requestBodyProcessingEnabled").getOrElse(false)
    val moesifApplicationId = config.get[String]("moesifApplicationId")
    val moesifCollectorEndpoint = config.get[String]("collectorEndpoint")
    MoesifApiFilterConfig(maxApiEventsToHoldInMemory, moesifApplicationId, reqBodyProcessing, moesifCollectorEndpoint)
  }
}

/**
  * The gzip filter configuration provider.
  */
@Singleton
class MoesifApiFilterConfigProvider @Inject()(config: Configuration) extends Provider[MoesifApiFilterConfig] {
  lazy val get = MoesifApiFilterConfig.fromConfiguration(config)
}

